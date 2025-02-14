// Student_random.java: sample implementation for Student
// COS 445 HW1, Spring 2018
// Created by Andrew Wonnacott

import java.util.Arrays;
import java.util.List;

public class Student_period implements Student {
  private class School implements Comparable<School> {
    public School(int i, double quality) {
      index = i;
      this.quality = quality;
    }

    private int index;
    private double quality;

    public int compareTo(School n) { // smaller pairs are higher quality
      int ret = Double.compare(n.quality, quality);
      return (ret == 0) ? (Integer.compare(index, n.index)) : ret;
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

    double qualityScaling = 1;
    double synergyScaling = 1;
    if (aptitude <= 0.25*S) {
      qualityScaling = 0.25;
      synergyScaling = 1.75;
    }
    else if (aptitude <= 0.5*S) {
      qualityScaling = 0.5;
      synergyScaling = 1.5;
    }
    else if (aptitude <= 0.75*S) {
      qualityScaling = 0.75;
      synergyScaling = 1.25;
    }
    for (int i = 0; i != synergies.size(); ++i) {
        double quality = schools.get(i);
        double synergy = synergies.get(i);
        preferences[i] = new School(i, quality * qualityScaling + synergy * synergyScaling);
    }

    Arrays.sort(preferences);

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
      double synergy, double scalingFactor) {

        double value = quality + synergy;
        double probability = (aptitude + scalingFactor * synergy);
        double expectedValue = value * probability;
        
        return expectedValue;

    }
}



