package com.uclab.leanuxplatform.RankVote;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class Arbiter {
	private static final int MAX_CANDIDATE = 100;
	private static final int MAX_VOTER = 1000;
	public static final String UNKNOWN = "unknown";
	
	private Candidate candidate;
	private int candidateNum;
	private Voter voter;
	private int voterNum;
	private HashMap<String, Integer> status = new HashMap<String, Integer>();
	private List<String> maxCandidateName = new ArrayList<String>();
	
	public void collect(Candidate candidate, Voter voter) {
		this.candidate = candidate;
		this.candidateNum = candidate.getCandidateNum();
		this.voter = voter;
		this.voterNum = voter.getVoterNum();
	}
	
	private void voteCount() {
		for(int i = 0; i < candidateNum; i++) {
			status.put(candidate.getCandidateName(i), 0);
		}
		for(int i = 0; i < voterNum; i++) {
			String selectedCandidateName = voter.getSelectedCandidateName(i);
			if (!selectedCandidateName.equals(UNKNOWN)) {
				status.put(voter.getSelectedCandidateName(i), status.get(voter.getSelectedCandidateName(i)) + 1);
			}
		}
		//System.out.println(status);
	}
	
	private void findMaxCandidateName() {
		int max = 0;
		for(int i = 0; i < candidateNum; i++) {
			String candidateName = candidate.getCandidateName(i);
			if(status.get(candidateName) > max) {
				maxCandidateName.clear();
				maxCandidateName.add(candidateName);
				max = status.get(candidateName);
			}else if(status.get(candidateName) == max) {
				maxCandidateName.add(candidateName);
			}
		}
	}
	
	public String getElectedCandidateName() {
		String electedCandidateName = UNKNOWN;
		
		voteCount();
		findMaxCandidateName();
		if(maxCandidateName.size() == 1) {	// Only one max voted candidate
			electedCandidateName = maxCandidateName.get(0);
		}else {	// More than one max voted candidate
			int minRank = MAX_VOTER;
			for(int i = 0; i < maxCandidateName.size(); i++) {
				String candidateName = maxCandidateName.get(i);
				for(int j = 0; j < voterNum; j++) {
					if(candidateName.equals(voter.getSelectedCandidateName(j))) {
						int voterRank = voter.getRank(j);
						if(voterRank < minRank) {
							minRank = voterRank;
							electedCandidateName = candidateName;
						}
					}
				}
			}
		}
		
		return electedCandidateName;
	}
}
