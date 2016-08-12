package co.edu.usbcali.gusrodli.squirrelbin.list;

import co.edu.usbcali.gusrodli.squirrelbin.list.dto.Item;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import java.util.Map;

public class SquirrelbinListService implements RequestHandler<String, Item[]>
{
    AmazonDynamoDBClient dynamoDB = new AmazonDynamoDBClient();
    ScanRequest scanRequest = new ScanRequest().withTableName("acorns");

    @Override
    public Item[] handleRequest(String input, Context context)
    {
        LambdaLogger logger = context.getLogger();

        ScanResult result = dynamoDB.scan(scanRequest);
        Item[] items = new Item[result.getCount()];
        int i = 0;
        for (Map<String, AttributeValue> item : result.getItems())
        {
            Item newItem = new Item();
            newItem.setId(item.get("id").getS());
            newItem.setAuthor(item.get("author").getS());
            newItem.setName(item.get("name").getS());
            items[i] = newItem;
        }

        logger.log("the retrieval has finished");
        return items;
    }

}
