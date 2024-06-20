//package com.kapuria.journalapp.config;

import java.util.Scanner;

public class abc {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        int t=sc.nextInt();

        for(int i=0;i<t;i++){
            int n=sc.nextInt();
            int[] arr=new int[n];
            for(int j=0;j<n;j++){
                arr[j]=sc.nextInt();
            }

            int ans=0;
            if(arr[0]==0){
                ans=1;
            }
            int max=arr[0];
            int prefix=arr[0];

            for(int j=1;j<n;j++){
                prefix+=arr[j];
                max=Math.max(arr[j],max);
                if(prefix-max==max){
                    ans++;
                }
            }
            System.out.println(ans);

        }
    }
}
