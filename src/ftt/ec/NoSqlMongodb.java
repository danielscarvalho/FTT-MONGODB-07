package ftt.ec;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.bson.Document;

import com.mongodb.ConnectionString;
//import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

// https://mongodb.github.io/mongo-java-driver/4.0/driver/getting-started/quick-start/
// https://mvnrepository.com/artifact/org.mongodb/mongodb-driver/3.12.7

public class NoSqlMongodb {

	public static void main(String[] args) {
		
		int counter = 100;
		
		// TODO Auto-generated method stub
		System.out.println("NoSQL MongoDB - " + new Date());

		MongoClient mongoClient = MongoClients.create("mongodb://127.0.0.1:27017");
		
		//MongoClient mongoClient = (MongoClient) new MongoClients();
		//MongoClient mongoClient = new MongoClient();

		
		MongoDatabase database = mongoClient.getDatabase("ftt");
		
		MongoCollection<Document> collection = database.getCollection("docs");
		
		long unixTime = Instant.now().getEpochSecond();		
		
		for (int c=0; c < 10; c++ ) {
		
			Document doc = new Document("name", "MongoDB")
                .append("type", "database")
                .append("count", 1)
                .append("now", new Date())
                .append("FTT", "EC")
                .append("seq", counter+c)
                .append("versions", Arrays.asList("v3.2", "v3.0", "v2.6"))
                .append("info", new Document("x", 203).append("y", 102)
                .append("docset", unixTime)
                		);
		
			System.out.print(doc);
			
			collection.insertOne(doc);
		
		} //for
		
		Random rand = new Random();
		
		List<Document> documents = new ArrayList<Document>();
		
		for (int i = 0; i < 100; i++) {
			
		    documents.add(new Document("i", i).append("doc-type", "fttdoc").append("value", rand.nextInt()*100));
		    
		}
		
		collection.insertMany(documents);
		
		System.out.println(collection.countDocuments());
		
		//doc-type	:"fttdoc"
		
		//MongoCollection myDocs 
		Document doc1 = collection.find(Filters.eq("doc-type","fttdoc")).first();
		
		System.out.println(doc1.toJson());
				
		MongoCursor<Document> cursor = collection.find(Filters.eq("doc-type","fttdoc")).iterator();
		
		try {
		    while (cursor.hasNext()) {
		        System.out.println(cursor.next().toJson());
		    }
		} finally {
		    cursor.close();
		}
		
		//MongoCollection<Document> docs = collection.find(Filters.eq("doc-type","fttdoc")).cursor();
		
		//System.out.println(doc1.toJson());
		
	} //main

} //NoSqlMongodb
