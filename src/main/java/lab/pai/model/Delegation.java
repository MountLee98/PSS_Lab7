package lab.pai.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.springframework.data.relational.core.mapping.Table;

@Entity
//@Table
public class Delegation {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long delegationId;
	@Column(name = "description")
	private String description;
	@Column(name = "dateTimeStart", nullable = false)
	private LocalDateTime dateTimeStart;
	@Column(name = "dateTimeStop", nullable = false)
	private LocalDateTime dateTimeStop;
	@Column(name = "travelDietAmount", columnDefinition = "integer default 30")
	private Integer travelDietAmount;
	@Column(name = "breakfastNumber", columnDefinition = "integer default 0")
	private Integer breakfastNumber;
	@Column(name = "dinnerNumber", columnDefinition = "integer default 0")
	private Integer dinnerNumber;
	@Column(name = "supperNumber", columnDefinition = "integer default 0")
	private Integer supperNumber;
	@Column(name = "transportType")
	private Transport transportType;
	@Column(name = "ticketPrice")
	private float ticketPrice;
	@Column(name = "autoCapacity")
	private AutoCapacity autoCapacityl;
	@Column(name = "km")
	private float km;
	@Column(name = "accomodationPrice")
	private float accomodationPrice;
	@Column(name = "otherTicketPrice")	
	private float otherTicketsPrice;
	@Column(name = "otherOutlayDesc")
	private String otherOutlayDesc;
	@Column(name = "otherOutlayPrice")
	private float otherOutlayPrice;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "userId")
//    private User user;
	
	public Delegation() {

	}
	
	public Delegation(LocalDateTime dateTimeStart, LocalDateTime dateTimeStop) {
        this.dateTimeStart = dateTimeStart;
        this.dateTimeStop = dateTimeStop;
	}
//    public Delegation(LocalDateTime dateTimeStart, LocalDateTime dateTimeStop, User user) {
//        this.dateTimeStart = dateTimeStart;
//        this.dateTimeStop = dateTimeStop;
//        this.user = user;
//    }
//	public User getUser() {
//		return user;
//	}
//	public void setUser(User user) {
//		this.user = user;
//	}
	
	
	public Long getDelegationId() {
		return delegationId;
	}
	public float getAccomodationPrice() {
		return accomodationPrice;
	}


	public void setAccomodationPrice(float accomodationPrice) {
		this.accomodationPrice = accomodationPrice;
	}


	public void setDelegationId(Long delegationId) {
		this.delegationId = delegationId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDateTime getDateTimeStart() {
		return dateTimeStart;
	}
	public void setDateTimeStart(LocalDateTime dateTimeStart) {
		this.dateTimeStart = dateTimeStart;
	}
	public LocalDateTime getDateTimeStop() {
		return dateTimeStop;
	}
	public void setDateTimeStop(LocalDateTime dateTimeStop) {
		this.dateTimeStop = dateTimeStop;
	}
	public Integer getTravelDietAmount() {
		return travelDietAmount;
	}
	public void setTravelDietAmount(Integer travelDietAmount) {
		this.travelDietAmount = travelDietAmount;
	}
	public Integer getBreakfastNumber() {
		return breakfastNumber;
	}
	public void setBreakfastNumber(Integer breakfastNumber) {
		this.breakfastNumber = breakfastNumber;
	}
	public Integer getDinnerNumber() {
		return dinnerNumber;
	}
	public void setDinnerNumber(Integer dinnerNumber) {
		this.dinnerNumber = dinnerNumber;
	}
	public Integer getSupperNumber() {
		return supperNumber;
	}
	public void setSupperNumber(Integer supperNumber) {
		this.supperNumber = supperNumber;
	}
	public Transport getTransportType() {
		return transportType;
	}
	public void setTransportType(Transport transportType) {
		this.transportType = transportType;
	}
	public float getTicketPrice() {
		return ticketPrice;
	}
	public void setTicketPrice(float ticketPrice) {
		this.ticketPrice = ticketPrice;
	}
	public AutoCapacity getAutoCapacityl() {
		return autoCapacityl;
	}
	public void setAutoCapacityl(AutoCapacity autoCapacityl) {
		this.autoCapacityl = autoCapacityl;
	}
	public float getKm() {
		return km;
	}
	public void setKm(float km) {
		this.km = km;
	}
	public float getOtherTicketsPrice() {
		return otherTicketsPrice;
	}
	public void setOtherTicketsPrice(float otherTicketsPrice) {
		this.otherTicketsPrice = otherTicketsPrice;
	}
	public String getOtherOutlayDesc() {
		return otherOutlayDesc;
	}
	public void setOtherOutlayDesc(String otherOutlayDesc) {
		this.otherOutlayDesc = otherOutlayDesc;
	}
	public float getOtherOutlayPrice() {
		return otherOutlayPrice;
	}
	public void setOtherOutlayPrice(float otherOutlayPrice) {
		this.otherOutlayPrice = otherOutlayPrice;
	}
	
	
}
