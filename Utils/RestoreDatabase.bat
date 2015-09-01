net stop Apache2.4
set PGPASSWORD=postgresql
cmd /c psql -h localhost -U postgres -d postgres -p 5432 -f %~d0%~p0..\DB\ReservaCanchasDB.sql
net start Apache2.4
