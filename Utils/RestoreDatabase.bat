net stop Apache2.4
cmd /c psql -h localhost -U postgres -W -f %~d0%~p0..\DB\ReservaCanchasDB.sql
net start Apache2.4