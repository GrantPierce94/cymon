package coms309.people;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * Controller used to showcase Create and Read from a LIST
 *
 * @author Vivek Bengre
 */

@RestController
public class monController {

    // Note that there is only ONE instance of PeopleController in
    // Springboot system.
    HashMap<String, Mon> monList = new HashMap<>();
    //CRUDL (create/read/update/delete/list)
    // use POST, GET, PUT, DELETE, GET methods for CRUDL

    // THIS IS THE LIST OPERATION
    // gets all the people in the list and returns it in JSON format
    // This controller takes no input.
    // Springboot automatically converts the list to JSON format
    // in this case because of @ResponseBody
    // Note: To LIST, we use the GET method
    @GetMapping("/mon")
    public @ResponseBody HashMap<String,Mon> getAllMons() {
        return monList;
    }



    // THIS IS THE CREATE OPERATION
    // springboot automatically converts JSON input into a person object and
    // the method below enters it into the list.
    // It returns a string message in THIS example.
    // in this case because of @ResponseBody
    // Note: To CREATE we use POST method
    @PostMapping("/mon")
    public @ResponseBody String createMon(@RequestBody Mon mon) {
        System.out.println(mon);
        monList.put(mon.getMon(), mon);
        return "New Monster "+ mon.getMon() + " Saved";
    }

    // THIS IS THE READ OPERATION
    // Springboot gets the PATHVARIABLE from the URL
    // We extract the person from the HashMap.
    // springboot automatically converts Person to JSON format when we return it
    // in this case because of @ResponseBody
    // Note: To READ we use GET method
    @GetMapping("/mon/{mon}")
    public @ResponseBody Mon getMonster(@PathVariable String mon) {
        String r = mon.substring(1, mon.length() - 1);
        Mon m = monList.get(r);
        return m;
    }

    // THIS IS THE UPDATE OPERATION
    // We extract the person from the HashMap and modify it.
    // Springboot automatically converts the Person to JSON format
    // Springboot gets the PATHVARIABLE from the URL
    // Here we are returning what we sent to the method
    // in this case because of @ResponseBody
    // Note: To UPDATE we use PUT method
    @PutMapping("/mon/{mon}")
    public @ResponseBody Mon updateMonster(@PathVariable String mon, @RequestBody Mon m) {
        String r = mon.substring(1, mon.length() - 1);
        monList.replace(r, m);
        return monList.get(r);
    }

    // THIS IS THE DELETE OPERATION
    // Springboot gets the PATHVARIABLE from the URL
    // We return the entire list -- converted to JSON
    // in this case because of @ResponseBody
    // Note: To DELETE we use delete method
    @DeleteMapping("/mon/{mon}")
    public @ResponseBody HashMap<String, Mon> deleteMonster(@PathVariable String mon) {
        String r = mon.substring(1, mon.length() - 1);
        monList.remove(r);
        return monList;
    }

    @PutMapping("/attack/{mon1}/{mon2}")
    public void attackMonster(@PathVariable String mon1, @PathVariable String mon2) {

        String r1 = mon1.substring(1, mon1.length() - 1);
        String r2 = mon2.substring(1, mon2.length() - 1);
        Mon m1 = monList.get(r1);
        Mon m2 = monList.get(r2);
        m2.setHp(m2.getHp() - m1.getAttack());

        if(m2.getHp() <= 0){
            monList.remove(r2);
        }

    }
}

