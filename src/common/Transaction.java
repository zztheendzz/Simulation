package common;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;

public class Transaction implements Serializable {
    private String code;
    private String accNum;
    private double amount;
    private String date;
    private String Type ;

    private String Result;

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String setDate(){
        Calendar cal = Calendar.getInstance();
        Date _date = cal.getTime();
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        date = sdf2.format(_date);
        return date;
    }

//get
    public  String getCode(){
        return code;
    }
    public  String getAccNum(){
        return accNum;
    }
    public  String getDate(){
        return date;
    }
    public  double getAmount(){
        return amount;
    }

    public String getResult() {
        return Result;
    }

    //set
    public  void setCode(String code){this.code= code;}
    public  void setAccNum(String accNum){this.accNum = accNum;}
    public  void setDate(String date){
        this.date = date;
    }
    public  void setAmount(double amount){
        this.amount = amount;
    }

    public void setResult(String result) {
        Result = result;
    }

    public  String _getAmount(){
        DecimalFormat df=new DecimalFormat("#,###,###");
        String amount = df.format(getAmount());
        return amount;
    }
    public  void _log(String code, String accNum, String date, double amount){
        this.code= code;
        this.accNum= accNum;
        this.date= date ;
        this.amount= amount;
    };
    public  void _log( String accNum, String date, double amount,String Type,String result){
        this.accNum= accNum;
        this.date= date ;
        this.amount= amount;
        this.Type = Type;
        this.Result=result;
    };
//    log

    public void log(){
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        System.out.println("+----------------------------------------------------+\n"+
                            "[GD]\t"+accNum+"|\t"+_getAmount()+"\tđ"+"|\t"+setDate()
        );
    }
    public void __log(){
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        System.out.println("+----------------------------------------------------------------------------------+\n"+
                "[GD]\t"+accNum+"|\t"+getType()+"|\t"+getResult()+"|\t"+_getAmount()+"\tđ"+"|\t"+date
        );
    }
}
