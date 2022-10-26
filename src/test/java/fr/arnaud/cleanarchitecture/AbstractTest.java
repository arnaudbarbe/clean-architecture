package fr.arnaud.cleanarchitecture;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;
import com.datastax.driver.core.Session;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import fr.arnaud.cleanarchitecture.infrastructure.publisher.entity.v1.ChampionshipPublisher;
import fr.arnaud.cleanarchitecture.infrastructure.publisher.entity.v1.LeaguePublisher;
import fr.arnaud.cleanarchitecture.infrastructure.publisher.entity.v1.MatchPublisher;
import fr.arnaud.cleanarchitecture.infrastructure.publisher.entity.v1.PlayerPublisher;
import fr.arnaud.cleanarchitecture.infrastructure.publisher.entity.v1.SeasonPublisher;
import fr.arnaud.cleanarchitecture.infrastructure.publisher.entity.v1.TeamPublisher;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CleanArchitectureApplication.class)
@ActiveProfiles({"test"})
@AutoConfigureMockMvc
public abstract class AbstractTest {
	public static final String DATETIME_FORMAT = "dd-MM-yyyy HH:mm:ss";
	public static final LocalDateTime FIXED_DATE = LocalDateTime.now();
	public static LocalDateTimeSerializer LOCAL_DATETIME_SERIALIZER = new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATETIME_FORMAT));

	protected ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected PlayerPublisher playerPublisher;

	@Autowired
	protected LeaguePublisher leaguePublisher;

	@Autowired
	protected SeasonPublisher seasonPublisher;

	@Autowired
	protected ChampionshipPublisher championshipPublisher;

	@Autowired
	protected MatchPublisher matchPublisher;

	@Autowired
	protected TeamPublisher teamPublisher;

	@Value("${spring.datasource.url}")
	protected String sqlUrl;

	@Value("${spring.datasource.username}")
	protected String sqlUserName;

	@Value("${spring.datasource.password}")
	protected String sqlPassword;

	@Value("${spring.data.cassandra.contact-points}")
	protected String contactPoint;
	
	@Value("${spring.data.mongodb.host}")
	protected String mongoHost;
	
	@Value("${spring.data.mongodb.port}")
	protected int mongoPort;
	
	@Value("${spring.data.mongodb.database}")
	protected String mongoDatabase;
			
	@BeforeEach
	public void setUp() throws SQLException {
		cleanDatabase();
	}
	
	@AfterEach
	public void tearDown() throws SQLException {
	}
	
	private void cleanDatabase() throws SQLException {

		// sql
		DataSource dataSource = setupDataSource(this.sqlUrl, this.sqlUserName, this.sqlPassword);
		String[] tablesName = new String[] { 
				"delete from team;",
				"delete from championship;",
				"delete from league;",
				"delete from season;"
				
				};

		Statement stmt = null;

		try (Connection conn = dataSource.getConnection()) {
			System.out.println("Creating connection.");
			stmt = conn.createStatement();
			System.out.println("Executing statements");

			for (String tableName : tablesName) {
				System.out.println(tableName + ":" + stmt.executeUpdate(tableName));
			}

			stmt.close();

		} catch (SQLException e) {
			throw e;
		} finally {
			shutdownDataSource(dataSource);
		}

		// cassandra
		Cluster cluster = null;
		Session session = null;
		try {
			Builder b = Cluster.builder().addContactPoint(this.contactPoint);
			cluster = b.build();
			session = cluster.connect();

			session.execute("truncate clean.player");
			
		} catch (Exception e) {
			if (session != null) {
				session.close();
			}
			if (cluster != null) {
				cluster.close();
			}
		}
		
		//mongo		
		try (MongoClient mongoClient = MongoClients.create("mongodb://" + this.mongoHost + ":" + this.mongoPort)) {
            MongoDatabase database = mongoClient.getDatabase(this.mongoDatabase);
            MongoCollection<Document> collection = database.getCollection("match");
            collection.deleteMany(new Document());
        }
	}

	private DataSource setupDataSource(final String sqlUrl, final String sqlUserName, final String sqlPassword) {
		BasicDataSource ds = new BasicDataSource();
		if (sqlUrl.startsWith("jdbc:h2")) {
			ds.setDriverClassName("org.h2.Driver");
		} else {
			ds.setDriverClassName("org.postgresql.Driver");
		}
		ds.setUrl(sqlUrl);
		ds.setUsername(sqlUserName);
		ds.setPassword(sqlPassword);

		return ds;
	}

	public static void shutdownDataSource(final DataSource ds) {
		try {
			BasicDataSource bds = (BasicDataSource) ds;
			bds.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
