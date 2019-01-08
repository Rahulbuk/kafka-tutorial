package com.wardziniak.kafka.app.basic;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.net.InetAddress;
import java.net.UnknownHostException;
//import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Conzumer {

    private KafkaConsumer consumer;

    public static void getParentheses(int openParenthesis, int closeParenthesis, String string) {
        if (openParenthesis == 0 && closeParenthesis == 0) {
            System.out.println(string);
        }

        if (openParenthesis > closeParenthesis) {
            return;
        }

        if (openParenthesis > 0) {
            System.out.println("openParenthesis: " + openParenthesis + " / closeParenthesis: " + closeParenthesis);
            getParentheses(openParenthesis - 1, closeParenthesis, string + "(");
        }

        if (closeParenthesis > 0) {
            System.out.println("openParenthesis: " + openParenthesis + " / closeParenthesis: " + closeParenthesis);
            getParentheses(openParenthesis, closeParenthesis - 1, string + ")");
        }
    }

    public static class Application{
        public Map<String, String> getUrlPages() {
            return null;
        }
    }

    static public class Arr {
        public String getName() {
            return null;
        }
    }

    public static void main(String[] args) {


        String input = "id string, name string, balances struct<netincome:decimal(10,4),grossincome:decimal(10,4)" +
                "ledger:struct<id:int,month:string>>";

        String seperatorRegex = ", ((?![^<>]*\\>)(?![^()]*\\)))";

        String[] splitInput = input.split(seperatorRegex);

        Arrays.stream(splitInput).forEach(d -> System.out.println(d));





//
//
//        Member memberOne = new Member(1);
//        Member memberTwo = new Member(2);
//        Member memberThree = new Member(3);
//        Member memberFour = new Member(4);
//        Member memberFive = new Member(5);
//        Member memberSix = new Member(6);
//        Member memberSeven = new Member(7);
//
//        memberTwo.setChildren(Arrays.asList(memberThree, memberFour));
//        memberFour.setChildren(Arrays.asList(memberFive, memberSix));
//
//        List<Member> memberList = Arrays.asList(memberOne, memberTwo, memberSeven);



    }









//    public static class Member
//    {
//        private List<Member> children;
//
//        private int memberId;
//
//        Member(int memberId)
//        {
//            this.memberId = memberId;
//        }
//
//        List<Member> getChildren()
//        {
//            return children;
//        }
//
//        void setChildren(List<Member> children)
//        {
//            this.children = children;
//        }
//
//        int getMemberId()
//        {
//            return memberId;
//        }
//
//        void setMemberId(int memberId)
//        {
//            this.memberId = memberId;
//        }
//
//        @Override
//        public String toString()
//        {
//            return String.valueOf(this.memberId);
//        }
//    }


}
