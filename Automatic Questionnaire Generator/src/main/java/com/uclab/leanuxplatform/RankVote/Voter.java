package com.uclab.leanuxplatform.RankVote;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class Voter {
	List<String> voterName = new ArrayList<String>();
	HashMap<String, Integer> rank = new HashMap<String, Integer>();
	HashMap<String, String> status = new HashMap<String, String>();
	
	public void load(List<String> voterName, HashMap<String, Integer> rank, HashMap<String, String> status) {
		this.voterName = voterName;
		this.rank = rank;
		this.status = status;
	}
	
	public String getVoterName(int idx) {
		return voterName.get(idx);
	}
	
	public Integer getRank(int idx) {
		return rank.get(voterName.get(idx));
	}
	
	public String getSelectedCandidateName(int idx) {
		return status.get(voterName.get(idx));
	}
	
	public int getVoterNum() {
		return voterName.size();
	}
}
