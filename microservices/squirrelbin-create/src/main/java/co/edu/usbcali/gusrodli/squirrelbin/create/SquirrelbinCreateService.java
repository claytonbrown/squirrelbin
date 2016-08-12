package co.edu.usbcali.gusrodli.squirrelbin.create;

import co.edu.usbcali.gusrodli.squirrelbin.create.dto.Request;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class SquirrelbinCreateService implements RequestHandler<Request, String>
{
    DynamoDB dynamoDB = new DynamoDB(new AmazonDynamoDBClient());
    Table table = dynamoDB.getTable("acorns");

    @Override
    public String handleRequest(Request input, Context context)
    {
        LambdaLogger logger = context.getLogger();
        logger.log("received : " + input);

        try
        {
            logger.log("trying to save item");
            Item item = new Item()
                .withPrimaryKey("id", input.getItem().id)
                .withString("name", input.getItem().getName())
                .withString("author", input.getItem().getAuthor());

            PutItemOutcome putItemOutcome = table.putItem(item);
            logger.log(String.valueOf(putItemOutcome));
        }
        catch (Exception e)
        {
            logger.equals("Create items failed.");
            logger.log(e.getMessage());
        }

        return String.valueOf(input);
    }
}
