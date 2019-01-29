select email from customer
union
select email from employeemr;

select cid as id1 from zzha.customer where email = 'q@q';

update customer
set email = 'c5', password = '1', name = 'sd', address = 'ert', phone = '123'
where cid = 47;

select password 
from customer
where email = 'c5';

select password from zzha.customer where email = 'c5';

select password from zzha.customer where email = 'jarvis@gmail.com';

select email from customer;


select * from zzha.Schedule_Service where cid = 1001;

drop table VEHICLE;

INSERT INTO "ZZHA"."SCHEDULE_SERVICE" (AID, SVID, SID, CID, LICENCE_ID, EID, SERVICE_DATE) VALUES (SCHEDULE_SEQUENCE.nextval, '18', '1', '1001', 'XYZ-5643', '557279280', '10-SEP-2018');

select * from Schedule_Service where SERVICE_DATE = '10-SEP-2018';

select pid, quantity from basic_service_need where bsid in (select bsid from maintenance_include where svid = 18) and cmid = (select car_model from maintenance_service where svid = 18);

update zzha.inventory set quantity = (select quantity from inventory where pid = 44 and sid = 1) - 1
where pid = 44 and sid = 1;

select pid, quantity from basic_service_need where bsid in (select bsid from maintenance_include where svid = 18) and cmid = (select car_model from maintenance_service where svid = 18);

select * from basic_service_need where bsid in (select bsid from repair_include where svid = 19) and cmid = 
(
select cmid 
from car_model, vehicle
where car_model.make = vehicle.make and car_model.model = vehicle.model and vehicle.licence_id = 'XYZ-5643');


select * from vehicle 
inner join own on own.licence_id = vehicle.licence_id
inner join car_model on vehicle.cmid = car_model.cmid
where own.cid = 47;
 
select ofd.doid, bs.name as bname, d.name as dname, pt.name as pname, ofd.quantity, ofd.datetime, ofd.status, ofd.expected_delivery_date, ofd.actual_delivery_date from order_from_distributor ofd, distributor d, service_center bs, part pt where ofd.bsid = bs.sid and ofd.sdid = d.did and ofd.pid = pt.pid and ofd.bsid = (select sid from employeemr_work_at where eid = 634622236);

select iv.sid, iv.quantity, iv.threshold from inventory iv, employeemr_work_at em where em.eid = 634622236 and em.sid <> iv.sid and iv.pid = 54;
select minimal_order from inventory iv, employeemr_work_at em where em.eid = 634622236 and em.sid = iv.sid and iv.pid = 54;

select ofd.soid, bs.name as bname, ss.name as sname, pt.name as pname, 
ofd.quantity, ofd.order_date, ofd.status, ofd.expected_delivery_date, ofd.actual_delivery_date 
from order_from_service_center ofd, service_center ss, service_center bs, part pt 
where ofd.bsid = bs.sid and ofd.ssid = ss.sid and ofd.pid = pt.pid 
and ofd.bsid = (select sid from employeemr_work_at where eid = 634622236) 
and ofd.pid = 24 order by ofd.soid desc;


SELECT n.nid, n.notify_time, n.doid, D.NAME, ob.expected_delivery_date, n.delayed_by FROM NOTIFICATION N, ORDER_FROM_DISTRIBUTOR OB, DISTRIBUTOR D
WHERE N.DOID = OB.DOID AND OB.SDID = D.DID AND ob.bsid = (SELECT SID FROM employeemr_work_at WHERE EID = 634622236);

SELECT ob.doid, ob.datetime, pt.name as pname, D.NAME as dname, s.name as bname, ob.quantity as qty, pt.price, ob.status
FROM NOTIFICATION N, ORDER_FROM_DISTRIBUTOR OB, DISTRIBUTOR D, service_center s, part pt
WHERE N.DOID = OB.DOID AND OB.SDID = D.DID AND ob.doid = 1 and ob.pid = pt.pid and ob.bsid = s.sid;
