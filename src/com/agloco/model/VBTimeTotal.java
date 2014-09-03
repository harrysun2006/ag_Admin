package com.agloco.model;

import java.util.Calendar;

public class VBTimeTotal {

	private Long memberId;
	private Long totalSecond;
	private Long selfTime;
	private Long directReferralTime;
	private Long extendedReferralTime;
	private Calendar modifiedDate;

	public Long getDirectReferralTime() {
		return directReferralTime;
	}

	public void setDirectReferralTime(Long directReferralTime) {
		this.directReferralTime = directReferralTime;
	}

	public void setDirectReferralTime(long directReferralTime) {
		this.directReferralTime = new Long(directReferralTime);
	}

	public Long getExtendedReferralTime() {
		return extendedReferralTime;
	}

	public void setExtendedReferralTime(Long extendedReferralTime) {
		this.extendedReferralTime = extendedReferralTime;
	}

	public void setExtendedReferralTime(long extendedReferralTime) {
		this.extendedReferralTime = new Long(extendedReferralTime);
	}

	public Calendar getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Calendar modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Long getSelfTime() {
		return selfTime;
	}

	public void setSelfTime(Long selfTime) {
		this.selfTime = selfTime;
	}

	public void setSelfTime(long selfTime) {
		this.selfTime = new Long(selfTime);
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getTotalSecond() {
		return totalSecond;
	}

	public void setTotalSecond(Long totalSecond) {
		this.totalSecond = totalSecond;
	}

}
