package com.uclab.leanuxplatform.RankVote;

import java.util.List;
import java.util.ArrayList;

public class Candidate {
	List<String> candidateName = new ArrayList<String>();
	
	public void load(List<String> candidateName) {
		this.candidateName = candidateName;
	}

	public String getCandidateName(int idx) {
		return candidateName.get(idx);
	}
	
	public int getCandidateNum() {
		return candidateName.size();
	}
}
