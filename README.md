*** Deprecated DO NOT USE ***

This repo was created from a decompilation of the com.vividsolutions:postgis-jts:1.5.3 jar, which was produced using [Java Decompiler (JD)](http://java-decompiler.github.io/).

This repo is considered legacy and is marked for deletion. Newer versions of Runway are directed here:
https://github.com/postgis/postgis-java

To find more up-to-date source.





*** PostGIS JDBC Driver extension README / FAQ ***

(C) 2005 Markus Schaber <markus.schaber@logix-tt.com>

$Id $

* What is it all about? *

JDBC is an database driver specification for Java. Like ODBC in the C
world, JDBC allows java applications to transparently use different
JDBC compliant databases without any source code changes. PostgreSQL,
the database PostGIS is written for, comes with a driver that
follows this specification. For downloads and more info, see:
http://jdbc.postgresql.org/download.html

The purpose of the JDBC Driver extension is to give the PostgreSQL
JDBC driver some understanding of the PostGIS data types (Geometry,
Box3D, Box2D). Without this, the Application can only get byte arrays
or strings (binary and text representation, rsp.) and has to parse it
on its own. When registering this extension, the Application can
simply call getObject(column) on the result of the query, and get a
real java object that is modeled after the OpenGIS spec. It also can
create or modify this objects itsself and then pass them into the
database via the PreparedStatement.setObject() method.

Currently, the code is tested with PostGIS 0.8.1, 0.9.1. 0.9.2 and
1.0.0. It supports both the new hex-encoded EWKB canonical text
representation used by PostGIS 1.0.0 lwgeom code, and the old, less
efficient WKT like representation used by previous releases when
reading data from the server. When sending data to the server, it
currently always uses the latter form, which is compatible to all
PostGIS versions.


* Do I need it? *

If you happen to write GIS applications, you can propably benefit.

In case your applications are PostGIS specific, you can fully exploit
the functionality, see "How to I use it" below for instructions and
the src/examples directory for some code examples.

If you rather prefer to stay OpenGIS compliant, then you cannot use
the full driver embedding, as this is PostGIS specific functionality.
But you can still use the geometry classes as a lightweight java
geometry model if you do not want to use a full-blown GIS
implementation like jts. Simply use the asText() and
GeometryFromText() OpenGIS SQL functions against whichever OpenGIS
compliant server you want, and use the WKT parsing constructors or
PGgeometry.geomFromString() as well as Geometry.toString() to convert
between WKT strings and geometry objects.


* Is it free? *

Yes. The actual Driver extension code is licensed under the GNU LGPL, 
this allows everyone to include the code in his projects. You do not
have to pay any license fees, and you can keep your own application 
code proprietary, but you have to make the PostGIS source code available 
to any receivers, including any modifications you apply to it. For 
details, please see the license file COPYING_LGPL.

The Build files and examples are licensed under GNU GPL, just like the
rest of PostGIS is. This is not LGPL as applications usually do not
link against those.


* How do I build it? *

You need a recent pgjdbc driver jar, see the download link from above.
It is currently tested and supported with 7.2, 7.3, 7.4, 8.0 and 8.1 pgjdbc
releases. Those are the same PostgreSQL releases that are supported by the
PostGIS server-side C code, and apart from 7.2, the same as currently 
supported by PostgreSQL server developers.

The current PostGIS jdbc release is also reported to run (but not compile)
against the postgresql jdbc7.1-1.2.jar, but this is not supported, as well as 
current PostGIS backend itsself does not support 7.1 servers any more.

Note that your pgjdbc driver version does not constrain the PostgreSQL 
server version. As the JDBC drivers are downwards compatible against older 
servers, and PostgreSQL servers typically accept older clients, you can 
easily use e. G. a pgjdbc 8.0 against a PostgreSQL 7.3 server. To benefit
from optimizations and bugfixes, it is generally suggested to use the 
newest stable pgjdbc build that is documented to work against your 
server release.

Make shure the pgjdbc driver is available in your Java CLASSPATH,
either by setting the environment variable, or by editing the
Makefile.

A "make jar" then compiles the code and creates two jar files. The
"postgis.jar" is for normal usage and deployment, the
"postgis_debug.jar" additionally includes the source code, for
debugging purposes.


* It is called jdbc2 - does it work with jdbc3, too? *

To make it short: The naming does not refer to SUN jdbc standard releases 
jdbc-1, jdbc-2 or jdbc-3. 

The current naming is somehow unfortunate. The directory simply is named
jdbc2 because it is the successor of Paul Ramsey's original jdbc directory,
which used to exist parallel in the CVS repository. As CVS does its best
to hinder useful version tracking across file renames, the name was kept
even after removal of the original jdbc subproject. 

Please note that the PostgreSQL JDBC driver itsself is released in
several flavours for different JDBC relases and sun JDK releases, but
currently, the same postgis.jar should work with all of them. If not,
you clearly found a bug, and we kindly ask you to report it.

If you run into troubles, make shure that you use the newest pgjdbc build.
Especially pre releases are known to contain bugs (that's why they are pre
releases), and e. G. 8.0 build 309 contained some problems that are fixed
in 8.0 build 313.


* How do I use it? *

To use the PostGIS types, you need the postgis.jar and the pgjdbc
driver in your classpath.

The PostGIS extension must be registered within the JDBC driver.
There are three ways to do this:

- If you use pgjdbc 8.0, the org/postgresql/driverconfig.properties
  file contained in the postgis.jar autoregisters the PostGIS
  extension for the PostGIS data types (geometry, box2d, box3d)
  within the pgjdbc driver.

- You can use the org.postgis.DriverWrapper as replacement for the
  jdbc driver. This class wraps the PostGreSQL Driver to
  transparently add the PostGIS Object Classes. This method currently
  works both with J2EE DataSources, and with the older DriverManager
  framework. I's a thin wrapper around org.postgresql.Driver that
  simply registers the extension on every new connection created.

  To use it, you replace the "jdbc:postgresql:" with a
  "jdbc:postgresql_postGIS" in the jdbc URL, and make your
  environment aware of the new Driver class.

  DriverManager users simply register org/postgis/DriverWrapper
  instead of  (or in addition to) org.postgresql.Driver, see
  examples/TestBoxes.connect()  for an working code.

  DataSource users similarly have to configure their datasource to
  use the different class. The following works for jboss, put it in
  your-ds.xml: <driver-class>org.postgis.DriverWrapper</driver-class>

- Of course, you can also manually register the Datatypes on your
  pgjdbc connection. You have to cast your connection to PGConnection
  and then call:

        pgconn.addDataType("geometry", "org.postgis.PGgeometry");
        pgconn.addDataType("box3d", "org.postgis.PGbox3d");
        pgconn.addDataType("box2d", "org.postgis.PGbox2d");

  You may need to dig through some wrappers when running in an
  appserver. E. G. for JBoss, The datasource actually gives you a
  instance of org.jboss.resource.adapter.jdbc.WrappedConnection and
  have to call getUnderlyingConnection() on it to get the
  PGConnection instance.)

  Also note that the above addDataType() methods known from earlier
  pgjdbc versions are deprecated in pgjdbc 8.0 (but still work), see
  the commented code variants in the DriverWrapper.addGisTypes()
  method for an alternative.

Note: Even using pgjdbc 8.0, you may still want to use the second or
third approach if you have several pgjdbc extensions that
autoregister for the same PostGIS types, as the driver cannot guess
which extension it should actually use on which connection. The
current pgjdbc implementation simply parses all
org/postgresql/driverconfig.properties the classloader can find in his
classpath, using the first definition found for each type.


* How to I run the tests? Are they allowed to fail? *

There are two types of tests provided, offline and online. Offline
tests can run without a PostGIS server, the online tests need a
PostGIS server to connect to.

- Offline Tests

  The easiest way to run the offline tests is "make offlinetests".

  The offline tests should always complete without any failure. If
  you happen to get a failure here, it is a bug in the PostGIS code
  (or, very unlikely, in the JDK/JRE or Hardware you use). Please
  contact the PostGIS developer list in this case.

- Online tests

  The online tests can be ran with "make onlinetests", but they need
  a specially prepared database to connect against, and the pgjdbc
  driver available in your classpath. The Makefile provides defaults
  for PGHOST, PGPOR, PGDATABASE, PGUSER and PGPASS, you can override
  them for your needs. For the jtest, the user needs to create and
  drop table privileges, the two other tests do not use any table.
  Make shure you have the PostGIS installed in the database.

  None of the online tests should report any failure. However, some of the
  tests are skipped against PostGix 0.X servers as 0.8.X and 0.9.X, those
  are the box2d tests (because this datatype simply is missing on those
  releases), as well as 22 skipped tests for some geometry representations
  that those old releases do not support. This is a PostGIS server side
  problem as the server fails to parse some OpenGIS compliant WKT
  representations, and structurally (but not geometrically or topologically) 
  mangles some other geometries. They are unlikely to be fixed in those
  releases as users should migrate to PostGIS 1.0.
  
  The Autoregister Test needs a pgjdbc version 8.0 or newer, and will
  simply do nothing with older pgjdbc versions.

  If you get any failure messages in the online tests, check whether
  your really fulfil the prerequisites above. If yes, please contact
  the PostGIS developer list.


* What about the JTS stuff *

There's beta support for the JTS 1.6 geometry implementations instead of the
native PostGIS classes on the java client side. Simply add jts_1.6.jar to your
CLASSPATH, "make postgis_jts" and use at your own risk.


* How can I contact you? *

Well, the best place are the official PostGIS mailing lists for PostGIS,
subscription information is linked at:
  http://postgis.refractions.net/support.php
  
If you want to report errors, please try to send us all the details we need
to reproduce your problem. A small, self-contained test case would be best.


* Phew. That's all? *

Yes. For now, at least.

Happy Coding!
