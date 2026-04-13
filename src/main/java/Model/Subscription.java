package Model;

import java.io.Serializable;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static watchit.watchit_project.WatchitApp.*;

public class Subscription implements Serializable {
    private static final long serialVesionUID=1L;
    private long personID;
    private String plan;
    private double price;
    private LocalDate startDate;
    private LocalDate endDate;
    private int maxMovies;
    private int numberOfMoviesWatched;
    int this_plan_count=0;

    public static final String BASIC_PLAN = "Basic";
    public static final String STANDARD_PLAN = "Standard";
    public static final String PREMIUM_PLAN = "Premium";

    public static final double BASIC_PRICE = 5.0;
    public static final double STANDARD_PRICE = 10.0;
    public static final double PREMIUM_PRICE = 15.0;

    public static final int BASIC_MAX_MOVIES = 5;
    public static final int STANDARD_MAX_MOVIES = 10;
    public static final int PREMIUM_MAX_MOVIES = 30;



    private static int subscriptionCount = 0;
    private static double totalRevenue = 0.0;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public Subscription(User user, Subscription sub) {/// user buy new sub
        this.personID = user.getID();
        this.plan=sub.plan;
        this.price=sub.price;
        this.maxMovies=sub.maxMovies;
        this.startDate = LocalDate.now();
        this.endDate = startDate.plusDays(30);
        this.numberOfMoviesWatched = 0;
        user.sub=this;
        totalRevenue+=sub.price;
        allSubscription.add(this);
        int x= available_subscriptions.indexOf(sub);
        available_subscriptions.get(x).this_plan_count++;
    }
    public Subscription(String name, double price, int max_movies){///admin add new sub
        this.set_plan_name(name);
        this.maxMovies=max_movies;
        this.price=price;
    }

    public void set_plan_name(String s){
        for (Subscription availableSubscription : available_subscriptions) {
            if (availableSubscription.plan.equals(s)) {
                System.out.println("There is a plan with the same name. Please choose different one");
                return;
            }
        }
        this.plan=s;
    }
    public void create_new_subscription(String name,double price,int max_movies){
        available_subscriptions.add(new Subscription(name,price,max_movies));
    }
    public void edit_subscription_name(Subscription sub,String new_name){
        int x=available_subscriptions.indexOf(sub);
        available_subscriptions.get(x).plan=new_name;
    }
    public void edit_subscription_price(Subscription sub,double price){
        int x=available_subscriptions.indexOf(sub);
        available_subscriptions.get(x).price=price;
    }
    public void edit_subscription_maxMovies(Subscription sub,int max_movies){
        int x=available_subscriptions.indexOf(sub);
        available_subscriptions.get(x).maxMovies=max_movies;
    }
    public void delete_subscription(Subscription sub){
        available_subscriptions.remove(sub);
    }


    public long getPersonID() {
        return personID;
    }

    public double getPrice() {
        return price;
    }

    public LocalDate getStartDate() {
        return startDate;
    }



    public LocalDate getEndDate() {
        return endDate;
    }

    public int getMaxMovies() {
        return maxMovies;
    }

    public int getNumberOfMoviesWatched() {
        return numberOfMoviesWatched;
    }

    public void setNumberOfMoviesWatched(int numberOfMoviesWatched) {
        this.numberOfMoviesWatched = numberOfMoviesWatched;
    }

    public String getPlan() {
        return plan;
    }




    public boolean isActive() {
        LocalDate currentDate = LocalDate.now();
        return !currentDate.isAfter(endDate) && numberOfMoviesWatched < maxMovies;

    }



    public static void deleteSubscription_of_user(int id) {// by id
        int ind=-1;
        for (User user:users){
            if(user.getID()==id){
                ind= users.indexOf(user);
                break;
            }
        }
        if(ind==-1){
            System.out.println("No matching user found.");
            return;
        }
        else if (!users.get(ind).sub.isActive()) {
            System.out.println("Can't delete, Subscription for UserID " + users.get(ind).sub.getPersonID() + " is inactive");
            return;
        }
        if (allSubscription.remove(users.get(ind).sub)) {
            subscriptionCount--;
            totalRevenue -= users.get(ind).sub.getPrice();
            System.out.println("Subscription is successfully deleted for UserID: " + users.get(ind).sub.getPersonID());
        } else {
            System.out.println("No subscription found for UserID: " + users.get(ind).sub.getPersonID());
        }

    }

    public void updateSubscription(User user,Subscription sub) {
        new Subscription(user,sub);
    }


    public static List<String> mostPopularPlan() { // admin method
        int max=0;
        for(Subscription sub:available_subscriptions){
            if(sub.this_plan_count>max){max=sub.this_plan_count;}
        }
        List<String> mostPopularPlans = new ArrayList<>();
        for(Subscription sub:available_subscriptions){
            if(sub.this_plan_count==max){mostPopularPlans.add(sub.getPlan());}
        }
        return mostPopularPlans;
    }

    public static List<String> mostRevenueInMonth() {
        Map<Month, Double> monthlyRevenue = new HashMap<>();
        //calculate revenue of each month
        for (Subscription subscription : allSubscription) {
            LocalDate tempStartDate = subscription.getStartDate();
            Month month = tempStartDate.getMonth();
            double priceOfMonth = subscription.price;

            monthlyRevenue.put(month, monthlyRevenue.getOrDefault(month, 0.0) + priceOfMonth);
        }
        //find maximum revenue
        double maxRevenue = 0.0;
        for (double revenue : monthlyRevenue.values()) {
            if (revenue > maxRevenue) {
                maxRevenue = revenue;
            }
        }
        //collect all months with maximum revenue
        List<String> maxRevenueMonths = new ArrayList<>();
        for (Map.Entry<Month, Double> entry : monthlyRevenue.entrySet()) {
            if (entry.getValue() == maxRevenue) {
                maxRevenueMonths.add(entry.getKey().name());
            }
        }
        return maxRevenueMonths;
    }

    public static void displayTotalRevenue() {
        System.out.println("Total Revenue = " + totalRevenue + " $");
    }

    public static void displaySubscriptionCount() {
        System.out.println("Total subscriptions = " + subscriptionCount);
    }

    public boolean able_to_watch_a_movie() {
        if (!isActive()) {
            System.out.println("Can't watch movies,Subscription for UserID " + personID + " is inactive");
            return false;
        }
        if (numberOfMoviesWatched >= maxMovies) {
            System.out.println("Movies limit reached for UserID: " + personID + " upgrade your plan");
            return false;
        }
        System.out.println(" Hope you like the movie , Remaining movies: " + (maxMovies - numberOfMoviesWatched));
        return true;
    }

    public void alertEnd() {
        if (!isActive()) {
            System.out.println("Your subscription has already expired");// want to renew?
            return;
        }
        LocalDate currentDate = LocalDate.now();
        LocalDate threeDaysBeforeEnd = endDate.minusDays(3);
        LocalDate twoDaysBeforeEnd = endDate.minusDays(2);
        LocalDate oneDayBeforeEnd = endDate.minusDays(1);
        if (currentDate.equals(oneDayBeforeEnd)) {
            System.out.println("Alert : Your subscription will end in 1 day . Please renew soon");
        }
        else if (currentDate.equals(twoDaysBeforeEnd)) {
            System.out.println("Alert : Your subscription will end in 2 days . Please renew soon");
        }
        else if (currentDate.equals(threeDaysBeforeEnd)) {
            System.out.println("Alert : Your subscription will end in 3 days . Please renew soon");
        }
    }

    public void displaySubscriptionInfo() {
        String startDateFormatted = startDate.format(formatter);
        String endDateFormatted = endDate.format(formatter);

        System.out.println("UserID: " + personID);
        System.out.println("Plan: " + plan);
        System.out.println("Price: " + price + "$");
        System.out.println("Start Date: " + startDateFormatted);
        System.out.println("End Date: " + endDateFormatted);
        System.out.println("Active: " + (isActive() ? "Yes" : "No"));
        System.out.println("Movies Watched: " + numberOfMoviesWatched + "/" + maxMovies);

    }

    public static void displayAllSubscriptionInfo() {
        for (Subscription subscription : allSubscription) {
            subscription.displaySubscriptionInfo();
            System.out.println();
        }
    }

}