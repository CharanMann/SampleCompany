#!/bin/bash
#
# Script to start/stop/restart SampleCompany services
start() {
		cd /opt/forgerock/SampleCompany/CommonServices/
		/usr/bin/npm install &
		/usr/bin/node server.js &
		echo "********************** SampleCompany-CommonServices started ***********************"

		cd /opt/forgerock/SampleCompany/EmployeeApp/
		/usr/bin/npm install &
		/usr/bin/npm start &
		echo "********************** SampleCompany-EmployeeApp started ***********************"

		cd /opt/forgerock/SampleCompany/TravelApp/
		/usr/bin/npm install &
		/usr/bin/npm start &
		echo "********************** SampleCompany-TravelApp started ***********************"

		cd /opt/forgerock/SampleCompany/BenefitsApp/
		/usr/bin/npm install &
		/usr/bin/npm start &
		echo "********************** SampleCompany-BenefitsApp started ***********************"

		cd /opt/forgerock/SampleCompany/CustomerApp/
		/usr/bin/npm install &
		/usr/bin/npm start &
		echo "********************** SampleCompany-CustomerApp started ***********************"
}
# Stop the OpenAM and depedent services
stop() {
		cd /opt/forgerock/SampleCompany
    kill -9 $(ps -ef | grep 'server.js' | awk '{print $2}')
    echo "********************** Sample Company apps stopped ***********************"
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
