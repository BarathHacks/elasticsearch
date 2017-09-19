package com.elasticsearch.Example2;

import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class App {

	public static void main(String[] args) {
		try {

			@SuppressWarnings({ "resource", "unchecked" })
			TransportClient client = new PreBuiltTransportClient(Settings.builder()
					.put("cluster.name","elasticsearch").build())
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("10.99.16.89"), 9300));
			client.prepareIndex("kodcucom", "article", "1")
					.setSource(putJsonDocument("ElasticSearch: Java",
							"ElasticSeach provides Java API, thus it executes all operations "
									+ "asynchronously by using client object..",
							new Date(), new String[] { "elasticsearch" }, "Hüseyin Akdoğan"))
					.get();
			getDocument(client, "kodcucom", "article", "1");
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static Map<String, Object> putJsonDocument(String title, String content, Date postDate, String[] tags,
			String author) {

		Map<String, Object> jsonDocument = new HashMap<String, Object>();

		jsonDocument.put("title", title);
		jsonDocument.put("content", content);
		jsonDocument.put("postDate", postDate);
		jsonDocument.put("tags", tags);
		jsonDocument.put("author", author);

		return jsonDocument;
	}

	public static void getDocument(Client client, String index, String type, String id) {

		GetResponse getResponse = client.prepareGet(index, type, id).execute().actionGet();
		Map<String, Object> source = getResponse.getSource();

		System.out.println("------------------------------");
		System.out.println("Index: " + getResponse.getIndex());
		System.out.println("Type: " + getResponse.getType());
		System.out.println("Id: " + getResponse.getId());
		System.out.println("Version: " + getResponse.getVersion());
		System.out.println(source);
		System.out.println("------------------------------");

	}

}
