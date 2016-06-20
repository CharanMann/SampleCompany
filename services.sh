#!/bin/bash
#
# Script to start/stop/restart SampleCompany services
start() {
		cd /opt/forgerock/SampleCompany/CommonServices/
		/usr/local/bin/node server.js &
		echo "********************** SampleCompany-CommonServices started ***********************"

		cd /opt/forgerock/SampleCompany/EmployeeApp/
		/usr/local/bin/npm install &
		/usr/local/bin/npm start &
		echo "********************** SampleCompany-EmployeeApp started ***********************"

		cd /opt/forgerock/SampleCompany/TravelApp/
		/usr/local/bin/npm install &
		/usr/local/bin/npm start &
		echo "********************** SampleCompany-TravelApp started ***********************"

		cd /opt/forgerock/SampleCompany/CustomerApp/
		/usr/local/bin/npm install &
		/usr/local/bin/npm start &
		echo "********************** SampleCompany-CustomerApp started ***********************"

		/opt/forgerock/OpenIG1/bin/startup.sh
		echo "********************** OpenIG1 instance started ***********************"

		/opt/forgerock/OpenIG2/bin/startup.sh
		echo "********************** OpenIG2 instance started ***********************"

		/opt/forgerock/opendjis1/bin/start-ds
		echo "********************** OpenDJ Identity Store started ***********************"

		/opt/forgerock/OpenAM-Server1/bin/startup.sh
		echo "********************** OpenAM instance started ***********************"
}
# Stop the OpenAM and depedent services
stop() {
    killall node
    echo "********************** Sample Company apps stopped ***********************"

		/opt/forgerock/OpenIG1/bin/shutdown.sh
		echo "********************** OpenIG1 instance stoped ***********************"

		/opt/forgerock/OpenIG2/bin/shutdown.sh
		echo "********************** OpenIG2 instance stoped ***********************"

    /opt/forgerock/opendjis1/bin/stop-ds
    echo "********************** OpenDJ Identity Store stoped ***********************"

    /opt/forgerock/OpenAM-Server1/bin/shutdown.sh
    echo "********************** OpenAM instance stoped ***********************"
}

### services options ###
case "$1" in
  start)
        start
        ;;
  stop)
        stop
        ;;
  status)
        ps -ef|grep java
				ps -ef|grep node
        ;;
  restart)
        stop
        start
        ;;
  *)
        echo $"Usage: $0 {start|stop|restart|status}"
        exit 1
esac
exit 0
