// Student_random.java: sample implementation for Student
// COS 445 HW1, Spring 2018
// Created by Andrew Wonnacott

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Student_periodt implements Student {
  private class School implements Comparable<School> {
    public School(int i, double qualitySynergy, double quality) {
      index = i;
      this.qualitySynergy = qualitySynergy;
      this.quality = quality;
    }

    private int index;
    private double qualitySynergy;
    private double quality;

    public int compareTo(School n) { // smaller pairs are higher quality
      int ret = Double.compare(n.qualitySynergy, qualitySynergy);
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
    double relativeRank = aptitude/S;
    double maxQuality = -1;
    HashMap<School, Integer> map = new HashMap<>();

    School[] qualities = new School[schools.size()];

    for (int i = 0; i != synergies.size(); ++i) {
        double quality = schools.get(i);
        maxQuality = Math.max(quality, maxQuality);
        double synergy = synergies.get(i);
        School uni = new School(i, quality + synergy, quality);
        preferences[i] = uni;
        qualities[i] = uni;
    }

    double targetRank = relativeRank * maxQuality;
    // index of target uni in qualities[]
    int targetUni = -1;
    double rankDiff = Integer.MAX_VALUE;

    // sorting reach schools
    Arrays.sort(preferences);
    // sorting target schools
    Arrays.sort(qualities, (a,b) -> Double.compare(a.quality, b.quality));
    // find rank of "max quality of target uni"
    for (int i = 0; i != synergies.size(); ++i) {
      if (Math.abs(qualities[i].quality - targetRank) < rankDiff) {
        targetUni = i;
      }
    }
    
    double percentile = aptitude/S;
    int numReachSchools = 0;
    if (percentile >= 0.70){
      numReachSchools = 7;
    }
    else {
      numReachSchools = 3;
    }
    int[] ret = new int[10];
    for (int i = 0; i < numReachSchools; i++) {
      ret[i] = preferences[i].index;
      map.put(preferences[i], ret[i]);
    }
    School[] targetSchools = new School[targetUni+1];
    for (int i = targetUni; i >= 0; i--) {
      targetSchools[i] = qualities[i];
    }
    Arrays.sort(targetSchools);
    // keeps track of index in targetSchools[]
    int targetIndex = 0;
    for (int i = numReachSchools; i < 10; i++) {
      while (targetIndex < targetSchools.length && map.containsKey(targetSchools[targetIndex])) {
        targetIndex++;
      }
      if (targetIndex == targetSchools.length) break;
      School uni = targetSchools[targetIndex];
      ret[i] = uni.index;
      map.put(uni, uni.index);
    }

    // if (map.size() < 10) {

    // }

    return ret;
  }

}



