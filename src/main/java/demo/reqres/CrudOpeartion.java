package demo.reqres;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.*;
public class CrudOpeartion {
	public static void main(String[] args) {
		int num;
		RestAssured.baseURI="https://reqres.in/";
		num=1;
		while(true) {
			Response r1=RestAssured.given().get("/api/users?page="+num);
			int statusCode=r1.getStatusCode();
			if(statusCode!=200) {
				break;
			}
			HashSet<Integer> id=new HashSet<>(r1.path("data.id"));
			ArrayList<String> email=r1.path("data.email");
			ArrayList<String> firstname=r1.path("data.first_name");
			ArrayList<String> lastname=r1.path("data.last_name");
			ArrayList<String> avatar=r1.path("data.avatar");
			for(int i=0;i<id.size();i++) {
				System.out.println(id.toArray()[i]+" "+email.get(i)+" "+firstname.get(i)+" "+lastname.get(i)+" "+avatar.get(i));
			}
			if(id.size()==0) {
				break;
			}
			num++;
		}
		System.out.println("SECOND API STARTED");
		num=1;
		while(true) {
			Response r2=RestAssured.given().get("/api/users/"+num);
			int statusCode=r2.getStatusCode();
			if(statusCode!=200) {
				break;
			}
			int id1=r2.path("data.id");
			String email1=r2.path("data.email");
			String firstname1=r2.path("data.first_name");
			String lastname1=r2.path("data.last_name");
			String avatar1=r2.path("data.avatar");
			System.out.println(id1+" "+email1+" "+firstname1+" "+lastname1+" "+avatar1);
			num++;
		}
	}
}