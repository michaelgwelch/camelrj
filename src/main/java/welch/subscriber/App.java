package welch.subscriber;

import org.apache.camel.Body;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.impl.DefaultCamelContext;

import org.apache.camel.Exchange;
/**
 * Hello world!
 *
 */
public class App extends RouteBuilder
{
	
    public static void main(String... args) throws Exception {
        App app = new App();
        app.run();
    }

    private void run() throws Exception {
        CamelContext context = new DefaultCamelContext();
        //context.addComponent("properties", new PropertiesComponent("file:etc/com.ds.spyglass.cfg"));
        context.addRoutes(this);
        context.start();
        System.in.read();
        context.stop();
        System.exit(0);
    }

    public void configure() {
    	MapBean<RabbitEnvelope, ContentImage> envelopeBean = envelope -> envelope.message;

    	CallProc proc = new CallProc();
    	
    	String queueName = "rabbitmq://localhost/completed_images?vhost=images&username=guest&password=guest&exchangeType=fanout&autoDelete=false";
    	from(queueName).routeId("RabbitMQ Reader")
    	.bean(BytesToStringBean.class)
    	.unmarshal().json(RabbitEnvelope.class, Object.class)
    	.bean(envelopeBean)
    	.bean(proc, "invoke")
    	.end();
    }
}

//slf4j-log4j12.jar
/*
package com.ds;

import org.apache.camel.builder.RouteBuilder;


public class SpyglassRouteBuilder extends RouteBuilder {

	private static final int BATCH_SIZE=500;
	private static final int TIMEOUT=1500;

	public void configure() {

		//Read from the following path
		//split the messages into rows.
		from("{{inputUri}}").routeId("Excel File Splitter")
				.log("Processing File: ${headers.CamelFileName}")
				.split().method(new ExcelSplitterBean(), "splitBody").streaming()
				.bean(new RowValidator(), "processRow")
				.to("seda:excel_row_mapper")
				.end();

		//Map the generic row to an object type.
		from("seda:excel_row_mapper").routeId("Excel Row Mapper")
				.bean(new SpyGlassExcelBean(), "processRow")
				.to("seda:xslt_mapping");

		//Transform Spyglass to DSPX via XSLT
		from("seda:xslt_mapping").routeId("XLST Mapper")
				.marshal().jacksonxml()
				.to("{{xsltFileName}}")
				.to("seda:note_splitter");

		from("seda:note_splitter").routeId("DSPX Note Splitter")
				.bean(new DspxNoteSplitterBean())
				.to("seda:dspx_aggregator");

		from("seda:dspx_aggregator").routeId("DSPX Aggregator")
				.aggregate(exchangeProperty("ExchangeUUID"),new DspxCatalogAggregationStrategy()).completionSize(BATCH_SIZE).completionTimeout(TIMEOUT)
				.to("seda:writer");

		from("seda:writer").routeId("File Writer")
				.to("{{outputUri}}?fileName=${file:name.noext}-${exchangeProperty.ExchangeUUID}.xml&fileExist=Append")
				.end();
	}

}
*/