package SignIn.backend.Controller;

import SignIn.backend.Model.User;
import SignIn.backend.Repository.UserRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;


    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @PostMapping("/user")
    String createUser(@RequestBody User user) {

        if (user == null) {
            return failure;
        }

        userRepository.save(user);

        //find the user in the mysql and return some info back to AS

        JSONObject ret = new JSONObject();
        User temp      = userRepository.findById(user.getId());
        
	ret.put("id", user.getId());
        ret.put("name", temp.getName());
        ret.put("email", temp.getEmail());
        
	return ret.toString();
    }


    @DeleteMapping("/delete")
    String removeUser(@RequestBody int id) {

        try {
            String nameDeleted = userRepository.findById(id).getName();
            userRepository.deleteById(id);
            return "Deleted: " + nameDeleted + " " + id;
        } catch (Exception e) {
            return failure;
        }

    }

}
