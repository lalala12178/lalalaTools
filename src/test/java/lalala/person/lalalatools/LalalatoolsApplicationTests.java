package lalala.person.lalalatools;

import lalala.person.lalalatools.service.jwtbomber.JwtBomberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


//Junit5使用ExtendWith加载Spring测试模块
@ExtendWith(SpringExtension.class)
@SpringBootTest
class LalalatoolsApplicationTests {

    @Autowired
    JwtBomberService jwtBomberService;

    @Test
    public void test1(){
        //Junit5使用Assertions实现断言
        //arg1: 期望值 arg2: 结果值
        jwtBomberService.dictCrack();
    }

}
