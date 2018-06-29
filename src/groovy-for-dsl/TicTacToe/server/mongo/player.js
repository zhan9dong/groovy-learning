db.player.drop();
db.player.insert(
    {
        _id: new ObjectId(),
        playerId: "1234567890",
        name: "Random",
        playerClass: "RandomPlayer"
    }
);
db.player.insert(
    {
        _id: new ObjectId(),
        playerId: "2345678901",
        name: "Random Blocking",
        playerClass: "RandomBlockingPlayer"
    }
);
db.player.insert(
    {
        _id: new ObjectId(),
        playerId: "3456789012",
        name: "Next Free Space",
        playerClass: "NextFreeSpacePlayer"
    }
);