package ra.md4.service.user;

import ra.md4.dto.request.FormLogin;
import ra.md4.dto.request.FormRegister;
import ra.md4.dto.response.UserInfo;
import ra.md4.models.User;

import java.util.List;

public interface IUserService {
    UserInfo login(FormLogin request);
    UserInfo register(FormRegister request);
    List<User> getAll();
    User findById(Integer id);
    void changeStatus(Integer id);
    void changeUserRole(Integer id);
    List<User> searchName(String name);
}
