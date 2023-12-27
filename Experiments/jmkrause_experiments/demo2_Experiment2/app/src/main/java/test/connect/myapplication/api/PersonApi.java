package test.connect.myapplication.api;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import test.connect.myapplication.model.Person;


public interface PersonApi {

    @GET("/")
    Call<Person> index();

    @GET("person/all")
    Call<List<Person>> GetAllPerson();

    @POST("person/post/{f}/{l}")
    Call<Person> PostPersonByPath(@Path("f") String firstName, @Path("l") String lastName);

    @POST("person/post")
    Call<Person> PostPersonByBody(@Body Person newPerson);

    @DELETE("person/delete/{f}/{l}")
    Call<Person> DeletePersonByPath(@Path("f") String firstName, @Path("l") String lastName);

    @DELETE("person/delete")
    Call<Person> DeletePersonByBody(@Body Person personToDelete);

}
