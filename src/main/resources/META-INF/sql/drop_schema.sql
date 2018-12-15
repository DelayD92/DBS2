-- <DROP TABLES AND SEQUENCES IF THEY EXIST> --------------------------------------------------------------------------------------------------------------------
begin
for dropi in (select 'drop ' || object_type || ' ' || object_name || ' CASCADE CONSTRAINTS' foo
from user_objects
where object_type in ('TABLE'))
loop
execute immediate dropi.foo;
end loop;

for dropi in (SELECT 'drop ' || object_type || ' ' || object_name ||
DECODE(OBJECT_TYPE, 'TABLE', ' CASCADE CONSTRAINTS', '') foo
from user_objects)
loop
execute immediate dropi.foo;
end loop;
exception WHEN OTHERS THEN NULL;
end;
purge recyclebin;
commit;
/
-- </DROP TABLES AND SEQUENCES IF THEY EXIST> ------------------------------------------------------------------------------------------------------------------
