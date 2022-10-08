db.createUser(
    {
        user: "clean",
        pwd: "clean",
        roles: [
            {
                role: "readWrite",
                db: "clean"
            }
        ]
    }
)