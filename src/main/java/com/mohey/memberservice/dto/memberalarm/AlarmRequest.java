package com.mohey.memberservice.dto.memberalarm;import javax.validation.constraints.NotEmpty;import lombok.AllArgsConstructor;import lombok.Getter;import lombok.NoArgsConstructor;import lombok.Setter;@NoArgsConstructor@Setter@AllArgsConstructor@Getterpublic class AlarmRequest {	@NotEmpty	private String alarmUuid;}