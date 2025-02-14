// Student_random.java: sample implementation for Student
// COS 445 HW1, Spring 2018
// Created by Andrew Wonnacott

import java.util.Arrays;
import java.util.List;

public class Student_okperiod implements Student {
  private class School implements Comparable<School> {
    public School(int i, double expectation) {
      index = i;
      this.expectation = expectation;
    }

    private int index;
    private double expectation;

    public int compareTo(School n) { // smaller pairs are higher quality
    // //   int ret = Double.compare(n.quality, quality);
    //   return (ret == 0) ? (Integer.compare(index, n.index)) : ret;
        return 0;
    }
  }

  public int[] getApplications(
      int N,
      double S,
      double T,
      double W,
      double aptitude,
      List<Double> schools,
      List<Double> synergies) {

    School[] preferences = new School[schools.size()];
    double qualitySum = 0;
    for (int i  = 0; i < N; i++) {
        qualitySum += schools.get(i);
    }
    
    for (int i = 0; i != synergies.size(); ++i) {
        double quality = schools.get(i);
        double synergy = synergies.get(i);
      preferences[i] = new School(i, findExpectedValue(S, W, aptitude, 
                        quality, synergy));
    }


    Arrays.sort(preferences, (a,b) -> Double.compare(b.expectation, a.expectation));

    int[] ret = new int[10];
    for (int i = 0; i != 10; ++i) {
      ret[i] = preferences[i].index;
    }
    return ret;
  }

  private double findExpectedValue(
      double S,
      double W,
      double aptitude,
      double quality,
      double synergy) {

        double value = quality + synergy;
        double probability = (aptitude + synergy)/(S + W);
        double expectedValue = value * probability;
        
        return expectedValue;

    }
}



