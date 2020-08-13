package com.ehb.dnd.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoDbClient {
    private static MongoDbClient uniqueInstance;
    private MongoClient client;

    public MongoDbClient getInstance()
    {
        if (uniqueInstance == null)
        {
            uniqueInstance = new MongoDbClient();
        }
        return uniqueInstance;
    }

    public MongoClient getClient()
    {
        if (this.client == null)
        {
            Logger.getLogger("org.mongodb.driver").setLevel(Level.WARNING);
            ConnectionString connectionString = new ConnectionString(MongoDb.connectionString);
            CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
            CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
            MongoClientSettings clientSettings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .codecRegistry(codecRegistry)
                    .build();
            this.client = MongoClients.create(clientSettings);
        }
        return this.client;
    }

}
