package pl.czarsiak.demo.Controllers;

import org.springframework.web.bind.annotation.*;
import pl.czarsiak.demo.Model.User;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/users")
public class UserController {

    private List<User> users = new ArrayList<>();

    public UserController() {
        this.users = buildUsers();
    }

    @GetMapping
    public List<User> getUsers(){
        return this.users;
    }

    @GetMapping(value = "/{id}")
    public User getUser(@PathVariable("id") Long id){
        return this.users.stream().filter(user -> user.getId() == id).findFirst().orElse(null);
    }

    @PostMapping
    public User saveUser(@RequestBody User user){
        Long nextId = 0L;
        if(this.users.size() != 0){
            User lastUser = this.users.stream().skip(this.users.size() - 1).findFirst().orElse(null);
            nextId = lastUser.getId() + 1;
        }
        user.setId(nextId);
        this.users.add(user);
        return user;
    }
    @PutMapping
    public User updateUser(@RequestBody User user){
        User modifiedUser = this.users.stream().filter(u -> u.getId() == user.getId()).findFirst().orElse(null);
        modifiedUser.setFirstName(user.getFirstName());
        modifiedUser.setLastName(user.getLastName());
        modifiedUser.setEmail(user.getEmail());
        return modifiedUser;
    }

    @DeleteMapping(value = "/{id}")
    public boolean deleteUser(@PathVariable Long id){
        User deleteUser = this.users.stream().filter(user -> user.getId() == id).findFirst().orElse(null);
            if(deleteUser != null){
                this.users.remove(deleteUser);
                return true;
            }else {
                return false;
            }

    }

     List<User> buildUsers() {

        List<User> users = new ArrayList<>();

        User user1 = buildUsers(1L,"Jan", "Kowalski", "jan@wp.pl");
        User user2 = buildUsers(2L,"Barbara", "Pawlak", "bar@wp.pl");
        User user3 = buildUsers(3L,"Pawel", "Nastula", "paw@wp.pl");
        User user4 = buildUsers(4L,"Monika", "Lewinski", "monlew@wp.pl");
        User user5 = buildUsers(5L,"Gor", "Tor", "gt@wp.pl");

        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);

        return users;
    }

     User buildUsers(Long id, String fname, String lname, String email) {
        User user = new User();
        user.setId(id);
        user.setFirstName(fname);
        user.setLastName(lname);
        user.setEmail(email);
        return user;
    }
}
