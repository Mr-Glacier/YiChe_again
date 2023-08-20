import Dao.Dao_sqlsever;
import Entity.Bean_Brand;
import Entity.Bean_Model;
import Entity.Model_Url;
import Until.SaveUntil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.ArrayList;

public class Model {
    public static void main(String[] args) {
        Dao_sqlsever dao_sqlsever = new Dao_sqlsever("T_YiCheBrand", 0);
        Dao_sqlsever dao_sqlsever1 = new Dao_sqlsever("T_YiCheBrand_page", 1);
        SaveUntil saveUntil = new SaveUntil();
        ArrayList<Object> brandList = dao_sqlsever.Method_Find();
        for (int i = 0; i < brandList.size(); i++) {
            String brand_url = ((Bean_Brand)brandList.get(i)).getbrand_url();
            String brand_id =((Bean_Brand)brandList.get(i)).getbrand_id();
            String brand_name = ((Bean_Brand)brandList.get(i)).getbrand_name();
            System.out.println(brand_url);
            try{
                Document document = Jsoup.parse(new URL(brand_url).openStream(),"UTF-8",brand_url);
                Elements Items1= document.select(".pagination-list-wrapper").select(".link-list.pg-item");
                if (Items1.size()!=0) {
                    System.out.println("buweikong");
                    System.out.println(Items1.size());
                    Elements Items2 =Items1.select("a");
                    System.out.println(Items2.size());
                    String model_url ="";
                    for (int j = 0; j < Items2.size(); j++) {
                        if (j==0){
                            model_url= brand_url;
                        }else {
                            model_url =brand_url+"&page="+(j+1);
                        }
                    }

                    Model_Url model_url1 = new Model_Url();
                    model_url1.setbrand_id(brand_id);
                    model_url1.setbrand_name(brand_name);
                    model_url1.setpage_url(model_url);
                    model_url1.setdownstate("否");

                  //  https://car.yiche.com/xuanchegongju/?mid=9&page=3

                }else {
                    saveUntil.Method_SaveFile_True("F:\\A_易车数据_811\\品牌文件\\为0的_url.txt",brand_url+"\n");
                }
            }catch (Exception ex){
                System.out.println(ex.toString());
                saveUntil.Method_SaveFile_True("F:\\A_易车数据_811\\品牌文件\\Error_url.txt",brand_url+"\n");
            }
        }
    }
}
