const positionSelectors = [".out.top.left", ".out.top.right", ".out.bottom.left", ".out.bottom.right"];

/**
 * @param {Number} maxInclusiveVal Max inclusive Integer must be > 0 ; If not given 1 is assumed
 * @returns {Number} random value between 0 and maxInclusiveVal
 */
function randomInt(maxInclusiveVal) {
    let val = maxInclusiveVal;
    if (typeof(val) !== "number") {
        val = 1;
    } else if (val < 1) {
        throw new Error("maxInclusiveVal must be at least 1");
    }
    return Math.floor(Math.random() * (val + 1))
}

/**
 * @returns {Boolean} randomly true or false
 */
function randomBoolean() {
    return (Math.random() < 0.5);
}

/**
 * @param {Number} val value from -100 to 100 (incusive)
 */
function setSteering(val) {
    let steeringLeftDiv = document.querySelector(".progressWrap.left .val");
    let steeringRightDiv = document.querySelector(".progressWrap.right .val");
    steeringLeftDiv.style.width="0px";
    steeringRightDiv.style.width="0px";
    if (val > 0) {
        steeringRightDiv.style.width= val + "%";
    } else if (val < 0) {
        steeringLeftDiv.style.width= (val * -1) + "%";
    }
}



/**
 * @param {Number} position 0 = top left, 1 = front right, 2 = bottom left, 3 = bottom right
 * @param {Number} value temperature in °C
 */
function setOuterWheelTemperature(position, value) {
    if (positionSelectors[position]) {
        setWheelTemperature(document.querySelector(positionSelectors[position] + " .wheel"), value);
    }
}


/**
 * @param {Number} position 0 = top left, 1 = front right, 2 = bottom left, 3 = bottom right
 * @param {Number} value temperature in °C
 */
function setInnerWheelTemperature(position, value) {
    if (positionSelectors[position]) {
        setWheelTemperature(document.querySelector(positionSelectors[position] + " .wheel .inner"), value);
    }
}

/**
 * Use setInnerWheelTemperature or setOuterWheelTemperature for setting.
 * In this method just adapt the color string
 * @param {HTMLDivElement} div
 * @param value temperature in °C
 */
function setWheelTemperature(div, value) {
    //color is a valid value for css backgroud
    let color;
    if (value < 80) {
        color = "green";
    }
    else if (value < 90) {
        color = "yellow";
    }
    else if (value < 100) {
        color = "red";
    }
    else {
        color = "purple";
    }
    div.style.background = color;
}

/**
 * @param {Number} position  0 = top left, 1 = front right, 2 = bottom left, 3 = bottom right
 * @param value temperature in °C
 */
function setBreakTemperature(position, value) {
    //color is a valid value for css backgroud
    if (positionSelectors[position]) {
        let div = document.querySelector(positionSelectors[position] + " .break");
        let color;
        if (value < 80) {
            color = "green";
        }
        else if (value < 90) {
            color = "yellow";
        }
        else if (value < 100) {
            color = "red";
        }
        else {
            color = "purple";
        }
        div.style.background = color;
    }
}

/**
 * @param {Number} position  0 = top left, 1 = front right, 2 = bottom left, 3 = bottom right
 * @param value temperature in °C
 */
function setAxisTemperature(position, value) {
    //color is a valid value for css backgroud
    if (positionSelectors[position]) {
        let div = document.querySelector(positionSelectors[position] + " .axis");
        let color;
        if (value < 80) {
            color = "green";
        }
        else if (value < 90) {
            color = "yellow";
        }
        else if (value < 100) {
            color = "red";
        }
        else {
            color = "purple";
        }
        div.style.background = color;
    }
}

/**
 * @param {Number} val value from -100 to 100 (incusive)
 */
function setThrottle(val) {
    let steeringTopDiv = document.querySelector(".progressWrap.top .val");
    steeringTopDiv.style.width="0px";

    steeringTopDiv.style.width= val + "%";

}

/**
 * @param {Number} val value from -100 to 100 (incusive)
 */
function setBrake(val) {
    let steeringBottomDiv = document.querySelector(".progressWrap.bottom .val");
    steeringBottomDiv.style.width="0px";
    steeringBottomDiv.style.width= (val * -1) + "%";

}

/**
 * @param {Number} val speed 0..x
 */
function setSpeed(val) {
    document.getElementById("speed").innerText = val;
}

/**
 * @param {Number} val rpm 0..x
 */
function setRpm(val) {
    let div = document.getElementById("rpm");
    div.innerText = val;
    div.style.color = "black";
    if (val > 8000) {
        div.style.color = "green";
    }
    if (val > 10000) {
        div.style.color = "red";
    }
    if (val > 12000) {
        div.style.color = "purple";
    }
}


/**
 * Generic Webservice Websocket Endpoint
 */
class TelemWsEndpoint {
    constructor() {
        /**
         * The WebSocket itself
         * @type WebSocket
         **/
        this.webSocket = undefined;
        /**
         * the protocol (ws or wss)
         * @type {string}
         */
        this.protocol = window.location.protocol.startsWith("https") ? "wss" : "ws";
        /**
         * The hostname (location.hostname)
         * @type {string}
         */
        this.hostname = window.location.hostname;
        /**
         * the port (either empty string or ":portNumber")
         * @type {string}
         */
        this.port = window.location.port ? (":" + window.location.port) : "";
        /**
         * the endpoint
         * @type {string}
         */
        this.endpoint = "/telem/endpoint";
    }
    /**
     * @return {string} the websocket endpoint url
     */
    getServerUrl() {
        return this.protocol + "://" + this.hostname + this.port + this.endpoint;
    }
    /**
     * Open the connection
     */
    connect() {
        if (this.webSocket && this.webSocket.readyState === WebSocket.OPEN) {
            throw new Error()
        }
        this.webSocket = new WebSocket(this.getServerUrl());
        this.webSocket.onopen = function (event) {
            console.debug('onopen::', event);
        }
        this.webSocket.onmessage = function (event) {
            console.debug('onmessage::', event);
      


            let message = JSON.parse(event.data);

            if (message.axisTemperatureTopLeft != null) {
                setAxisTemperature(0, message.axisTemperatureTopLeft);
            }
            if (message.axisTemperatureTopRight != null) {
                setAxisTemperature(1, message.axisTemperatureBottomRight);
            }
            if (message.axisTemperatureBottomLeft != null) {
                setAxisTemperature(2, message.axisTemperatureBottomLeft);
            }
            if (message.axisTemperatureBottomRight != null) {
                setAxisTemperature(3, message.axisTemperatureBottomRight);
            }
            if (message.breakTemperatureTopLeft != null) {
                setBreakTemperature(0, message.breakTemperatureTopLeft);
            }
            if (message.breakTemperatureTopRight != null) {
                setBreakTemperature(1, message.breakTemperatureBottomRight);
            }
            if (message.breakTemperatureBottomLeft != null) {
                setBreakTemperature(2, message.breakTemperatureBottomLeft);
            }
            if (message.breakTemperatureBottomRight != null) {
                setBreakTemperature(3, message.breakTemperatureBottomRight);
            }
            if (message.innerWheelTemperatureTopLeft != null) {
                setInnerWheelTemperature(0, message.innerWheelTemperatureTopLeft);
            }
            if (message.innerWheelTemperatureTopRight != null) {
                setInnerWheelTemperature(1, message.innerWheelTemperatureBottomRight);
            }
            if (message.innerWheelTemperatureBottomLeft != null) {
                setInnerWheelTemperature(2, message.innerWheelTemperatureBottomLeft);
            }
            if (message.innerWheelTemperatureBottomRight != null) {
                setInnerWheelTemperature(3, message.innerWheelTemperatureBottomRight);
            }
            if (message.outerWheelTemperatureTopLeft != null) {
                setOuterWheelTemperature(0, message.outerWheelTemperatureTopLeft);
            }
            if (message.outerWheelTemperatureTopRight != null) {
                setOuterWheelTemperature(1, message.outerWheelTemperatureBottomRight);
            }
            if (message.outerWheelTemperatureBottomLeft != null) {
                setOuterWheelTemperature(2, message.outerWheelTemperatureBottomLeft);
            }
            if (message.outerWheelTemperatureBottomRight != null) {
                setOuterWheelTemperature(3, message.outerWheelTemperatureBottomRight);
            }
            if (message.rpm != null) {
                setRpm(message.rpm);
            }
            if (message.speed != null) {
                setSpeed(message.speed);
            }
            if (message.steering != null) {
                setSteering(message.steering);
            }
            if (message.throttle != null) {
                setThrottle(message.throttle);
            }
            if (message.brake != null) {
                setBrake(message.brake);
            }

        }
        this.webSocket.onclose = function (event) {
            console.debug('onclose::', event);
            endpoint.connect();
        }
        this.webSocket.onerror = function (event) {
            console.debug('onerror::', event);
        }
    }

    /**
     * @return {number} the readyState of the Websocket connection, which is one of {@link WebSocket#OPEN}, {@link WebSocket#CLOSED}, {@link WebSocket#CLOSING}, {@link WebSocket#CONNECTING}
     */
    getStatus() {
        if (!this.webSocket) {
            return WebSocket.CLOSED;
        }
        return this.webSocket.readyState;
    }

    /**
     * Disconnect the websocket
     * @throws Error if websocket is not open
     */
    disconnect() {
        console.debug("disconnect")
        if (this.webSocket && this.webSocket.readyState === WebSocket.OPEN) {
            this.webSocket.close();
        } else {
            throw new Error("Websocket is not open")
        }
    }
}
const endpoint = new TelemWsEndpoint();
try {
    window.onbeforeunload = function() { endpoint.disconnect(); };
    endpoint.connect();
} catch (e) {
    console.error("Cannot conect to Websocket", endpoint.getServerUrl(), e)
}
