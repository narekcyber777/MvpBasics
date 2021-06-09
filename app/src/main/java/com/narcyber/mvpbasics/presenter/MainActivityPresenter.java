package com.narcyber.mvpbasics.presenter;

import android.content.Context;

import androidx.arch.core.executor.DefaultTaskExecutor;

import com.google.gson.Gson;
import com.narcyber.mvpbasics.helper.DataSaveHelper;
import com.narcyber.mvpbasics.model.User;
import com.narcyber.mvpbasics.model.UserEmailPasswordStorage;

import java.util.List;

public class MainActivityPresenter  {

   private  MainActivityView view;
   private DataSaveHelper dataSaveHelper;
   private User user;

   private  static  final  String LOCAL="Local";


    public MainActivityPresenter(MainActivityView view, Context context) {
        this.view=view;
        dataSaveHelper= DataSaveHelper.getINSTANCE(context);
       newUserRequest();


    }




    public  void newUserRequest(){
        this.user=new User();
    }

    public  void setUserPassword(String password){
        this.user.setPassword(password);
    }
    public  void setUserEmail(String email){
        this.user.setEmail(email);
    }
    public  void findUserByEmailAndPassword(){

        if(this.user.getEmail()==null  || this.user.getPassword()==null){
            view.ifExistGetKey(null);
            return;
        }
      List users=  dataSaveHelper.getAllCurrentObjects(User.class);
        for (Object o : users) {
           User user=(User)o;
           if(user.getEmail().trim().equalsIgnoreCase(this.user.getEmail().trim())
                   && user.getPassword().trim().equalsIgnoreCase(this.user.getPassword())){
               view.ifExistGetKey(user.getId());

               return;
           }

        }

        view.ifExistGetKey(null);

    }
    //save values while checkbox is checked ;
      public  void rememberPasswordAndEmail(final String email,final  String password){
          UserEmailPasswordStorage userEmailPasswordStorage=new UserEmailPasswordStorage(email,password);
        dataSaveHelper.writeObject(LOCAL,userEmailPasswordStorage,UserEmailPasswordStorage.class);




      }


      public  void  sendPasswordAndEmailLastRegistered(){
        Object obj=dataSaveHelper.readObject(LOCAL,UserEmailPasswordStorage.class);

        if(obj!=null){
            UserEmailPasswordStorage userEmailPasswordStorage=(UserEmailPasswordStorage)obj;
            view.savedPasswordAndEmail(userEmailPasswordStorage.getEmail(),userEmailPasswordStorage.getPassword());
        }
      }

      public  void removeLocal(){

        dataSaveHelper.removeObject(LOCAL);

      }







    public  interface  MainActivityView{
    void ifExistGetKey(final String key);
    void savedPasswordAndEmail(final String email,String password);
    }
}
