.TL
Build 3 Team 13
.AU
Iouri Goussev 5484750
Troy Taillefer 3499502
Justin Stefani 4305787
Pavel Lepin 9711082
.bp
.NH
Architecture
.PS
V : box width 2 height 1.5;
move to last box .n;
down;
move 0.25;
"\fIView";
move to V.c;
right;
box "\fISimViewer";
move to V.c;
left;
box "\fIWorldEditor";
move to V.s;
down;
line;
M : box width 3 height 1.5;
move to last box .n;
move 0.25;
"\fIDomain";
move to last box .w;
right;
move 0.25;
box "\fIActionLog";
box "\fIModel";
box "\fIEngine";
move to M.s;
down;
line;
P : box width 2 height 1.5;
move to P.n;
move 0.25;
"\fIPersistence";
move to P.c;
right;
box "\fISimWriter";
move to P.c;
left;
box "\fISimReader";
.PE
.bp
.NH
Concurrency
.NH 2
Memory Problems
.PS
A : box width 1 height 2;
move to A.n;
down;
move 0.25;
"\fICore 0";
move 0.25;
box "\fIRegisters";
move 0.25;
box "\fIL1 Cache";
move to A.ne;
right;
move;
down;
B : box width 1 height 2;
move to B.n;
down;
move 0.25;
"\fICore 1";
move 0.25;
box "\fIRegisters";
move 0.25;
box "\fIL1 Cache";
move to A.se;
down;
move;
C : box "\fIL2 Cache";
line;
box "\fIMain Memory";
line from A.s to C.n;
line from B.s to C.n;
.PE
.bp
.NH 2
Sequence Diagram
.PS
copy "sequence.pic"
object(A,"s1:State");
object(B,"s2:State");
object(C,"s3:Sate");
async();
step();
active(A);
active(B);
active(C);

message(A,B,"ATTACK");
message(C,B,"ATTACK");
step();
message(B,B,"BATTLE");
step();
message(B,C,"WIN");
message(B,A,"WIN");
step();
complete(A);
complete(B);
complete(C);
.PE



