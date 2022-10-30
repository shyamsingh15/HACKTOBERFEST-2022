package App;

import Entity.Player;
import Entity.Rank;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.*;

public class LudoGame {
    static Scanner sc = new Scanner(System.in);
    static  Random random = new Random();
    public static void main(String[] args){
        int n;
        System.out.print("Enter number of Player::");
        n= sc.nextInt();

        List<Player> playerList = new ArrayList<>();
        List<Integer> orderList = createPlayerList(n,playerList);
        System.out.println("Order of Players to play the game");
        orderList.forEach(System.out::println);
        List<Rank> rankList = playGame(playerList,n,orderList);
        showRankList(rankList);

        //===========database work====================
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        rankList.forEach(Rank -> session.save(Rank));
        transaction.commit();
        session.close();
        System.out.println("Rank List is stored in database");
        //================================================
    }

    //============== Create Random order player list=================================
    static List<Integer> createPlayerList(int n,List<Player> playerList){
        List<Integer> order = new ArrayList<>();
        for(int i=0;i<n;i++) {
            playerList.add(new Player((i + 1)));
            order.add((i+1));
        }
        Collections.shuffle(order);

        return order;
    }

    //==================Play Game========================================================
    static List<Rank> playGame(List<Player> playerList,int n,List<Integer> orderList){
        List<Rank> rankList = new ArrayList<>();
        int playerRank=1;
        int next=0;
        int totalScore=0;
        int id;
        while(rankList.size()!=playerList.size()){

            while(true) {
                if(next==n)
                    next=0;
                id = orderList.get(next++);
                boolean check = checkValidId(id, rankList, playerList);
                if(check)
                    break;
            }
            System.out.print("Player "+ (id) + " it's your turn press r to roll the dice:: ");
            checkValidInput(id);
            int num = random.nextInt(10);
            //int num = sc.nextInt();
            System.out.println("Your Current Score: "+(num+1));
            totalScore = addScore(playerList,(num+1),id);

            while((num+1)==10)
            {
                System.out.print("Congratulations Player "+ (id) + " You got another chance press r to roll the dice again:: ");
                checkValidInput(id);
                //num = random.nextInt(10);
                num= sc.nextInt();
                System.out.println("Your Current Score: "+(num+1));
                totalScore = addScore(playerList,(num+1),id);
                if(totalScore >=  30)
                {
                    break;
                }
            }

            if(totalScore>=30){
                System.out.println("** Player "+ (id) + " you have completed the game your rank is "+playerRank);
                rankList.add(new Rank(id,totalScore,playerRank++));
            }
            showCurrentScore(playerList);
        }
        return rankList;
    }

//=================Checking for Valid Input=====================================
    private static boolean checkValidId(int id,List<Rank> rankList,List<Player> playerList) {
        int flag=0;
        for (int i=0;i<rankList.size();i++) {
            if(rankList.get(i).id==id)
            {
                flag=1;
                break;
            }
        }
        Player player= playerList.get(id-1);
        if(player.secondScore==1 && player.firstScore==1) {
            flag = 1;
            System.out.println("\nSorry Player "+ id + "you can not proceed , you have penalty for two consecutive 1");
            player.firstScore= -1;
            player.secondScore= -1;
        }
        if(flag == 0)
            return true;
        else
            return false;

    }

    //===================Current Score Card=================================================
    static void showCurrentScore(List<Player> playerList){
        System.out.println("\n==================Score Card========================");
        System.out.println("Player_id      score");
        playerList.forEach(player -> System.out.println("       "+player.id+"      "+player.score));
    }

    //================Final Rank List========================================
    static void showRankList(List<Rank> rankList){
        System.out.println("\n==================Rank List========================");
        System.out.println("Player_id      score     Rank");
        rankList.forEach(rank -> System.out.println("       "+rank.id+"      "+rank.score+"      "+rank.rank));
    }

    //====================checking for Valid Input==================================================
    static void checkValidInput(int id){
        while (sc.next().charAt(0)!='r'){
            System.out.print("Player "+ (id) + " it's your turn press r to roll the dice:: ");
        }
    }

    //===================Adding Score to Player==============================================
    static int addScore(List<Player> playerList,int currentScore,int id){
        int totalScore=0;
        playerList.get(id-1).score+=currentScore;
        playerList.get(id-1).secondScore=playerList.get(id-1).firstScore;
        playerList.get(id-1).firstScore=(currentScore);
        totalScore=playerList.get(id-1).score;
        return totalScore;
    }
}

