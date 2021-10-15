package eu.kneipel.telem.ws;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TelemMessage {

  @JsonProperty
  private Integer steering;

  @JsonProperty
  private Integer throttle;

  @JsonProperty
  private Integer speed;

  @JsonProperty
  private Integer rpm;

  @JsonProperty
  private Integer innerWheelTemperatureTopLeft;

  @JsonProperty
  private Integer innerWheelTemperatureTopRight;

  @JsonProperty
  private Integer innerWheelTemperatureBottomLeft;

  @JsonProperty
  private Integer innerWheelTemperatureBottomRight;

  @JsonProperty
  private Integer outerWheelTemperatureTopLeft;

  @JsonProperty
  private Integer outerWheelTemperatureTopRight;

  @JsonProperty
  private Integer outerWheelTemperatureBottomLeft;

  @JsonProperty
  private Integer outerWheelTemperatureBottomRight;

  @JsonProperty
  private Integer breakTemperatureTopLeft;

  @JsonProperty
  private Integer breakTemperatureTopRight;

  @JsonProperty
  private Integer breakTemperatureBottomLeft;

  @JsonProperty
  private Integer breakTemperatureBottomRight;

  @JsonProperty
  private Integer axisTemperatureTopLeft;

  @JsonProperty
  private Integer axisTemperatureTopRight;

  @JsonProperty
  private Integer axisTemperatureBottomLeft;

  @JsonProperty
  private Integer axisTemperatureBottomRight;

  public TelemMessage setSpeed(int speed) {
    this.speed = speed;
    return this;
  }

  public TelemMessage setRpm(int rpm) {
    this.rpm = rpm;
    return this;
  }

  public TelemMessage setInnerWheelTemperatureTopLeft(int innerWheelTemperatureTopLeft) {
    this.innerWheelTemperatureTopLeft = innerWheelTemperatureTopLeft;
    return this;
  }

  public TelemMessage setInnerWheelTemperatureTopRight(int innerWheelTemperatureTopRight) {
    this.innerWheelTemperatureTopRight = innerWheelTemperatureTopRight;
    return this;
  }

  public TelemMessage setInnerWheelTemperatureBottomLeft(int innerWheelTemperatureBottomLeft) {
    this.innerWheelTemperatureBottomLeft = innerWheelTemperatureBottomLeft;
    return this;
  }

  public TelemMessage setInnerWheelTemperatureBottomRight(int innerWheelTemperatureBottomRight) {
    this.innerWheelTemperatureBottomRight = innerWheelTemperatureBottomRight;
    return this;
  }

  public TelemMessage setOuterWheelTemperatureTopLeft(int outerWheelTemperatureTopLeft) {
    this.outerWheelTemperatureTopLeft = outerWheelTemperatureTopLeft;
    return this;
  }

  public TelemMessage setOuterWheelTemperatureTopRight(int outerWheelTemperatureTopRight) {
    this.outerWheelTemperatureTopRight = outerWheelTemperatureTopRight;
    return this;
  }

  public TelemMessage setOuterWheelTemperatureBottomLeft(int outerWheelTemperatureBottomLeft) {
    this.outerWheelTemperatureBottomLeft = outerWheelTemperatureBottomLeft;
    return this;
  }

  public TelemMessage setOuterWheelTemperatureBottomRight(int outerWheelTemperatureBottomRight) {
    this.outerWheelTemperatureBottomRight = outerWheelTemperatureBottomRight;
    return this;
  }

  public TelemMessage setBreakTemperatureTopLeft(int breakTemperaturTopLeft) {
    this.breakTemperatureTopLeft = breakTemperaturTopLeft;
    return this;
  }

  public TelemMessage setBreakTemperatureTopRight(int breakTemperaturTopRight) {
    this.breakTemperatureTopRight = breakTemperaturTopRight;
    return this;
  }

  public TelemMessage setBreakTemperatureBottomLeft(int breakTemperaturBottomLeft) {
    this.breakTemperatureBottomLeft = breakTemperaturBottomLeft;
    return this;
  }

  public TelemMessage setBreakTemperatureBottomRight(int breakTemperaturBottomRight) {
    this.breakTemperatureBottomRight = breakTemperaturBottomRight;
    return this;
  }

  public TelemMessage setAxisTemperatureTopLeft(int axisTemperaturTopLeft) {
    this.axisTemperatureTopLeft = axisTemperaturTopLeft;
    return this;
  }

  public TelemMessage setAxisTemperatureTopRight(int axisTemperaturTopRight) {
    this.axisTemperatureTopRight = axisTemperaturTopRight;
    return this;
  }

  public TelemMessage setAxisTemperatureBottomLeft(int axisTemperaturBottomLeft) {
    this.axisTemperatureBottomLeft = axisTemperaturBottomLeft;
    return this;
  }

  public TelemMessage setAxisTemperatureBottomRight(int axisTemperaturBottomRight) {
    this.axisTemperatureBottomRight = axisTemperaturBottomRight;
    return this;
  }

  public TelemMessage setSteering(int steering) {
    this.steering = steering;
    return this;
  }

  public TelemMessage setThrottle(int throttle) {
    this.throttle = throttle;
    return this;
  }
}
