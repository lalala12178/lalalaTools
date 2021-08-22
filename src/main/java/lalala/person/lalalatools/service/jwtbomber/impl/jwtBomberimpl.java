package lalala.person.lalalatools.service.jwtbomber.impl;


import com.auth0.jwt.algorithms.Algorithm;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lalala.person.lalalatools.service.jwtbomber.JwtBomberService;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.Instant;
import java.util.*;

@Service
public class jwtBomberimpl implements JwtBomberService {


    //过期时间设置(30分钟)
    private static final long EXPIRE_TIME = 30*60*1000;

    //需要爆破的目标token
    private static final String TARGET_TOKEN ="eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJXZWJHb2F0IFRva2VuIEJ1aWxkZXIiLCJhdWQiOiJ3ZWJnb2F0Lm9yZyIsImlhdCI6MTYyOTYyNjI4OCwiZXhwIjoxNjI5NjI2MzQ4LCJzdWIiOiJ0b21Ad2ViZ29hdC5vcmciLCJ1c2VybmFtZSI6IlRvbSIsIkVtYWlsIjoidG9tQHdlYmdvYXQub3JnIiwiUm9sZSI6WyJNYW5hZ2VyIiwiUHJvamVjdCBBZG1pbmlzdHJhdG9yIl19.sCs42PUoidAupop6wsgLLzZFdLeljwFBft_5YAWUzzA";


    @Override
    public String dictCrack() {

        List<String> dictString = new ArrayList<>();


        //从文件中读取字典list，之后封装成一个公共方法
            String path = "G:\\javaWorkSpace\\lalalaTools\\src\\main\\java\\lalala\\person\\lalalatools\\dict\\google-10000-english-master\\20k.txt";
            File file = new File(path);
            StringBuilder result = new StringBuilder();
            try{
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));//构造一个BufferedReader类来读取文件

                String s = null;
                while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                    dictString.add(s);
                }
                br.close();
            }catch(Exception e){
                e.printStackTrace();
            }

        for (String dictWord:dictString){

            //过期时间和加密算法设置
            Date date=new Date(System.currentTimeMillis()+EXPIRE_TIME);
            Algorithm algorithm = null;
            try {
                algorithm = Algorithm.HMAC256(Base64.getEncoder().encodeToString(dictWord.getBytes("utf-8")));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            //头部信息
            Map<String,Object> header=new HashMap<>(2);
            //header.put("typ","aaaa");
            header.put("alg","HS256");



            String baseStr= new String();

            try {
                baseStr = Base64.getEncoder().encodeToString(dictWord.getBytes("utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            //这里设置token的payload部分
            String token02 = Jwts.builder()
                    .setHeader(header)
                    .setIssuer("WebGoat Token Builder")
                    .setAudience("webgoat.org")
                    .claim("iat", 1629626288)
                    .claim("exp", 1629626348)
                    .setSubject("tom@webgoat.org")
                    .claim("username", "Tom")
                    .claim("Email", "tom@webgoat.org")
                    .claim("Role", new String[]{"Manager", "Project Administrator"})
                    .signWith(SignatureAlgorithm.HS256,baseStr).compact();


            //爆破成功就输出该字典
            if(TARGET_TOKEN.equals(token02)){
                System.out.println(dictWord);
            }
        }


        return "";


    }
}
