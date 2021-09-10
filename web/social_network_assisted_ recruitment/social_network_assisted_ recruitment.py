import base64

import datetime
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText

from flask import Flask, render_template, request, session, jsonify
from DBConnection import Db

app = Flask(__name__)
app.secret_key="hii"


@app.route('/')
def hello_world():
    return render_template('index.html')
@app.route('/login_post',methods=['post'])
def login_post():
    uname=request.form["username"]
    password=request.form["pass"]
    db=Db()

    qry="select * from login where uname='"+uname+"' and password='"+password+"'"
    res=db.selectOne(qry)
    if res is not None:
        if res["type"]=="admin":
            return render_template('index_admin.html')
        else:
            return"no"
    else:
        return "invalid"

@app.route('/home')
def adminhome():
    return render_template('index_admin.html')

@app.route('/home')
def a():
    return render_template('home.html')

@app.route('/admin_add_skills')
def admin_add_skills():
    return render_template('add_skills.html')
@app.route('/add_skill_post',methods=['post'])
def add_skill_post():

    askill=request.form["textfield2"]
    adescription=request.form["textarea"]
    qry ="insert into skill(skill,description) VALUES ('"+askill+"','"+adescription+"')"
    db=Db()
    db.insert(qry)
    return '''<script>alert("skills added successfully");window.location="/home"</script>'''
@app.route('/admin_edit_skills/<id>')
def admin_edit_skills(id):
    qry="select * from skill where skill_id='"+id+"'"
    db=Db()
    res=db.selectOne(qry)
    session['sid']=id
    return render_template('edit_skill.html',data=res)
@app.route('/admin_delete_skills/<id>')
def admin_delete_skills(id):
    qry="delete from skill where skill_id='"+id+"'"
    db=Db()
    res=db.delete(qry)

    return admin_view_skills()
@app.route('/edit_skill_post',methods=['post'])
def edit_skill_post():
    eskill=request.form["textfield"]
    edescription=request.form["textarea"]
    qry="update skill set skill='"+eskill+"',description='"+edescription+"' where skill_id='"+str(session['sid'])+"'"
    db=Db()
    db.update(qry)
    return admin_view_skills()
@app.route('/admin_complaint_view')
def admin_complaint_view():
    qry="select complain_view.*,view_user.name,view_user.phone_no,view_user.loginid from complain_view,view_user where complain_view.ulid=view_user.loginid"
    db=Db()
    res=db.select(qry)
    return render_template('complaint_view.html',data=res)
@app.route('/admin_reply/<id>')
def admin_reply(id):
    session['cid']=id
    return render_template('reply.html')
@app.route('/reply_post',methods=['post'])
def reply_post():
    areply=request.form["textarea"]
    qry="update complain_view set reply='"+areply+"' where cid='"+str(session['cid'])+"'"
    db=Db()
    db.update(qry)
    return admin_complaint_view()
@app.route('/admin_view_skills')
def admin_view_skills():
    qry = "select * from skill"
    db = Db()
    res=db.select(qry)
    return render_template('view_skills.html',data=res)
@app.route('/admin_view_user')
def admin_view_user():
    qry="select * from view_user, login where login.loginid=view_user.loginid"
    db=Db()
    res=db.select(qry)
    return render_template('view_user.html',data=res)

@app.route('/admin_block_user/<lid>')
def admin_block_user(lid):
    session['blid']=lid
    return render_template("send_reason.html")

@app.route("/admin_block_user_post",methods=['post'])
def admin_block_user_post():
    lid=session['blid']
    db=Db()
    res=db.selectOne("select * from view_user where loginid='"+lid+"'")
    eml=res['email']
    reason=request.form['textarea']

    import smtplib
    s = smtplib.SMTP(host='smtp.gmail.com', port=587)
    s.starttls()
    s.login("worker.recruitment.2k21@gmail.com", "wrkr@2k21")
    msg = MIMEMultipart()  # create a message.........."
    msg['From'] = "worker.recruitment.2k21@gmail.com"
    msg['To'] = eml
    msg['Subject'] = "Account Blocked!!!"
    body = "Reason  : " + reason
    msg.attach(MIMEText(body, 'plain'))
    s.send_message(msg)

    db.update("update login set type ='blocked' where loginid='"+lid+"'")
    return admin_view_user()

@app.route('/admin_unblock_user/<lid>')
def admin_unblock_user(lid):
    db = Db()
    res = db.selectOne("select * from view_user where loginid='" + lid + "'")
    eml = res['email']

    import smtplib
    s = smtplib.SMTP(host='smtp.gmail.com', port=587)
    s.starttls()
    s.login("worker.recruitment.2k21@gmail.com", "wrkr@2k21")
    msg = MIMEMultipart()  # create a message.........."
    msg['From'] = "worker.recruitment.2k21@gmail.com"
    msg['To'] = eml
    msg['Subject'] = "Account Unblocked!!!"
    body = "Since the complaint against you has been proved fake, your account has been unblocked..."
    msg.attach(MIMEText(body, 'plain'))
    s.send_message(msg)

    db.update("update login set type ='user' where loginid='" + lid + "'")
    return admin_view_user()
# ------------android user----------------

@app.route('/and_login',methods=['post'])
def and_login():
    uname=request.form["username"]
    password=request.form["pass"]
    db=Db()
    qry="select * from login where uname='"+uname+"' and password='"+password+"'"
    res=db.selectOne(qry)
    if res is not None:
        if res['type']=="user":
            return jsonify(status="ok",lid=res['loginid'])
        else:
            return jsonify(status="blocked")
    else:
        return jsonify(status="no")

@app.route('/and_reg', methods=['post'])
def and_reg():
    db = Db()
    aname=request.form["name"]
    aphone_no=request.form["phone_no"]
    aplace=request.form["place"]
    apost=request.form["post"]
    aimage=request.form['image']
    aoccupation=request.form['occupation']
    aemail=request.form['email']
    askillid=request.form['skill_id']
    password=request.form['pass']
    a = base64.b64decode(aimage)
    dt = datetime.datetime.now()
    dd = str(dt).replace(" ", "_").replace(":", "_").replace("-", "_")
    fh = open("E:\\social_network_assisted_ recruitment\\static\\user_pic\\" + dd + ".jpg", "wb")
    path = "/static/user_pic/" + dd + ".jpg"
    fh.write(a)
    fh.close()
    lid=db.insert("insert into login VALUES (NULL ,'"+aemail+"','"+password+"','user')")
    qry = "insert into view_user VALUES(NULL,'"+aname+"','"+aphone_no+"','"+aplace+"','"+apost+"','"+path+"','"+aoccupation+"','"+aemail+"','"+str(lid)+"','"+askillid+"') "
    res = db.insert(qry)
    return jsonify(status='ok')
@app.route('/and_view_other_user', methods=['post'])
def and_view_other_user():
    db = Db()
    qry = "select * from view_user "
    res = db.select(qry)
    print(res)
    if res is not None:
        return jsonify(status="ok", users=res)
    else:
        return jsonify(status="no")
@app.route('/send_frnd_rqst',methods=['post'])
def send_frnd_rqst():
    db=Db()
    afromid=request.form["flid"]
    atoid=request.form["tlid"]

    qry="insert into friend_request values(NULL,'"+afromid+"','"+atoid+"','pending',curdate())"
    res=db.insert(qry)
    return jsonify(status="ok")
@app.route('/view_send_request',methods=['post'])
def view_send_request():
    db = Db()
    ulid=request.form['lid']
    print(ulid)
    qry = "select view_user.*,friend_request.* from view_user INNER JOIN friend_request ON friend_request.fromid =view_user.loginid  where friend_request.toid='"+ulid+"'"
    res = db.select(qry)
    print(qry)

    if res is not None:
        return jsonify(status="ok", users=res)
    else:
        return jsonify(status="no")
@app.route('/delete',methods=['post'])
def delete():
    reqid = request.form["reqid"]
    qry="delete from friend_request where reqid='"+reqid+"'"
    db=Db()
    res=db.delete(qry)

    return jsonify(status='ok')

@app.route('/view_recieve_request', methods=['post'])
def view_recieve_request():
    db = Db()
    ulid = request.form['lid']
    print(ulid)
    qry = "select view_user.*,friend_request.* from view_user INNER JOIN friend_request ON view_user.loginid=friend_request.fromid where friend_request.toid='" + ulid + "'and friend_request.status='pending'"
    res = db.select(qry)
    print(qry)
    if res is not None:
        return jsonify(status="ok", users=res)
    else:
        return jsonify(status="no")
@app.route('/view_my_friend',methods=['post'])
def view_my_friend():
    db=Db()
    ulid=request.form['lid']
    qry="select view_user.* , friend_request.* from view_user inner join friend_request where( (view_user.loginid='"+ulid+"' or friend_request.toid='"+ulid+"')and (friend_request.status='friend') and (friend_request.fromid = view_user.loginid)) "
    res=db.select(qry)
    print(res)
    if res is not None:
        return jsonify(status="ok", users=res)
    else:
        return jsonify(status="no")

@app.route('/accept',methods=['post'])
def accept():
    reqid = request.form["reqid"]
    db=Db()
    qry="update friend_request set status='friend' where reqid='"+reqid+"'"
    res=db.update(qry)
    print(res)
    return jsonify(status='ok')
@app.route('/reject',methods=['post'])
def reject():
    reqid = request.form["reqid"]
    db = Db()
    qry = "update friend_request set status='reject' where reqid='" + reqid + "'"
    res = db.update(qry)
    print(res)
    return jsonify(status='no')


@app.route('/post_add',methods=['post'])
def post_add():
    db=Db()
    aulid=request.form["lid"]
    afilepath=request.form["img"]
    adescription=request.form["dist"]
    a = base64.b64decode(afilepath)
    dt = datetime.datetime.now()
    dd = str(dt).replace(" ", "_").replace(":", "_").replace("-", "_")
    fh = open("E:\\social_network_assisted_ recruitment\\static\\post\\" + dd + ".jpg", "wb")
    path = "/static/post/" + dd + ".jpg"
    fh.write(a)
    fh.close()
    qry="insert into post values(NULL,'"+aulid+"','"+path+"','"+adescription+"',curdate())"
    res=db.insert(qry)
    if res is not None:
        return jsonify(status="ok", data=res)
    else:
        return jsonify(status="no")
@app.route('/post_view',methods=['post'])
def post_view():
    db = Db()
    ulid=request.form["ulid"]
    qry = "select * from post where ulid='"+ulid+"'"
    res = db.select(qry)
    print(qry)
    if res is not None:
        return jsonify(status="ok", users=res)
    else:
        return jsonify(status="no")

@app.route('/complaint_send',methods=['post'])
def complaint_send():
    db=Db()
    aulid=request.form["lid"]
    acomplaint=request.form["acomplaint"]

    qry="insert into complain_view values(NULL,'"+aulid+"','"+acomplaint+"',curdate(),'pending')"
    res=db.insert(qry)
    if res is not None:
        return jsonify(status="ok", data=res)
    else:
        return jsonify(status="no")

@app.route('/complaint_view', methods=['post'])
def complaint_view():
    db = Db()
    qry = "select * from complain_view"
    res = db.select(qry)
    if res is not None:
        return jsonify(status="ok", users=res)
    else:
        return jsonify(status="no")

@app.route('/and_view_skill',methods=['post'])
def and_view_skill():
    qry = "select * from skill"
    db = Db()
    res = db.select(qry)
    print(res)
    if res is not None:
        return jsonify(status="ok", users=res)
    else:
        return jsonify(status="no")

@app.route('/and_view_my_skill',methods=['post'])
def and_view_my_skill():
    qry = ""
    db = Db()
    res = db.select(qry)
    print(res)
    if res is not None:
        return jsonify(status="ok", users=res)
    else:
        return jsonify(status="no")
@app.route('/skill_base_search',methods=['post'])
def skill_base_search():
    db=Db()
    aname=request.form["skills"]
    qry="select view_user.*,skill.* from view_user INNER JOIN skill ON view_user.skill_id=skill.skill_id where skill.skill='"+aname+"'"
    res=db.select(qry)
    if res is not None:
        return jsonify(status="ok", users=res)
    else:
        return jsonify(status="no")
@app.route('/locinsertion', methods=["post"])
def locinsertion():
    db = Db()
    lid = request.form['lid']
    lat = request.form['latitude']
    lon = request.form['longitude']
    place = request.form['place']
    qry = "select * from user_location where lid='" + lid + "'"
    q = db.selectOne(qry)
    if q is not None:
        cc = "update user_location set latitude='" + lat + "',longitude='" + lon + "',place='"+place+"',date=curdate()  where lid='" + lid + "'"
        ee = db.update(cc)
        return jsonify(status="ok")
    else:
        ss="insert into user_location(lid,latitude,longitude,place,date)values('"+lid+"','"+lat+"','"+lon+"','"+place+"',curdate())"
        nn=db.insert(ss)
        return jsonify(status="ok")


@app.route('/us_near_by_person', methods=['post'])
def us_near_by_person():
    latti = request.form['lati']
    longi = request.form['logi']
    lid = request.form['lid']
    c = Db()
    qry = "SELECT view_user.*,user_location.*, SQRT( POW(69.1 * (Latitude - '" + latti + "'), 2) +POW(69.1 * ('" + longi + "' - Longitude) * COS(Latitude/ 57.3), 2)) AS distance FROM user_location,view_user where user_location.user_id=view_user.loginid  and view_user.loginid!='" + lid + "' ORDER BY distance "
    print(qry)
    res = c.select(qry)
    print(res)
    dist=res['distance']
    for i in res:
        dis = i['distance']
        if dis < 5000:
            qry = "select * from view_user "
            res = c.select(qry)
            return jsonify(status="yes",data="res")
        else:
            return jsonify(status="no")

    return jsonify(status="ok", data=res)

@app.route('/delete_post',methods=['post'])
def delete_post():
    db = Db()
    postid = request.form["postid"]
    qry="delete from post where postid='"+postid+"'"
    res=db.delete(qry)
    print(res)
    return jsonify(status='ok')
@app.route('/post_view_friends',methods=['post'])
def post_view_friends():
    ulid=request.form["lid"]
    db = Db()
    qry = "select post.* , friend_request.* from post inner join friend_request where( (post.ulid='"+ulid+"' or friend_request.toid='"+ulid+"')and (friend_request.status='friend') and (friend_request.fromid = post.ulid))"
    res = db.select(qry)
    print(qry)

    if res is not None:
        return jsonify(status="ok", users=res)
    else:
        return jsonify(status="no")
@app.route('/view_profile', methods=['post'])
def view_profile():
    ulid = request.form["lid"]
    db = Db()
    qry = "select view_user.*,skill.skill_id as sid,skill.skill from view_user INNER JOIN skill ON view_user.skill_id=skill.skill_id where view_user.loginid='" + ulid + "'"
    res = db.selectOne(qry)
    print(res)
    if res is not None:
        return jsonify(status="ok", name=res['name'],image=res['image'], phone_no=res['phone_no'], place=res['place'], post=res['post'],
                       occupation=res['occupation'], email=res['email'],skillid=res['sid'] ,skill=res['skill'])

    else:
        return jsonify(status="no")
@app.route('/edit_profile',methods=['post'])
def edit_profile():
    db=Db()
    ulid=request.form['lid']
    aname = request.form["name"]
    aphone_no = request.form["phone_no"]
    aplace = request.form["place"]
    apost = request.form["post"]
    aimage = request.form['image']
    aoccupation = request.form['occupation']
    aemail = request.form['email']
    if aimage=="aa":
        qry="update view_user set name='"+aname+"',phone_no='"+aphone_no+"',place='"+aplace+"',post='"+apost+"',occupation='"+aoccupation+"',email='"+aemail+"' where loginid='"+ulid+"'"
        res=db.update(qry)
        return jsonify(status='ok')
    else:
        a = base64.b64decode(aimage)
        dt = datetime.datetime.now()
        dd = str(dt).replace(" ", "_").replace(":", "_").replace("-", "_")
        fh = open("E:\\social_network_assisted_ recruitment\\static\\user_pic\\" + dd + ".jpg", "wb")
        path = "/static/user_pic/" + dd + ".jpg"
        fh.write(a)
        fh.close()
        qry = "update view_user set name='" + aname + "',image='" + path + "',phone_no='" + aphone_no + "',place='" + aplace + "',post='" + apost + "',occupation='" + aoccupation + "',email='" + aemail + "',skill_id='" + askill + "' where loginid='" + ulid + "'"
        res = db.update(qry)
        return jsonify(status='ok')



@app.route('/add_my_comment',methods=['post'])
def add_my_comment():
    db=Db()
    lid=request.form["lid"]
    comment=request.form["cm"]
    postid=request.form['pd']

    qry="insert into comment(postid,ulid,date,comment)values('"+postid+"','"+lid+"',curdate(),'"+comment+"')"
    res=db.insert(qry)
    return jsonify(status="ok")
@app.route('/view_comment', methods=['post'])
def view_comment():
    db = Db()
    psid=request.form['pid']
    qry = "select comment.*, view_user.* from comment inner join view_user on  comment.ulid=view_user.loginid and postid='"+psid+"' order by comment_id desc"
    res = db.select(qry)
    print(qry)
    if res is not None:
        return jsonify(status="ok", users=res)
    else:
        return jsonify(status="no")




@app.route('/view_alllikes', methods=['post'])
def view_alllikes():
    db = Db()
    psid=request.form['postid']
    lid=request.form['lid']
    qry = "select view_user.*,likes.likeid  from likes,view_user,post where post.postid=likes.postid and likes.userid=view_user.loginid and likes.postid='"+psid+"' order by likeid desc"
    res = db.select(qry)
    qry22="select count(likeno) as llk from likes where postid='"+psid+"'"
    res22=db.selectOne(qry22)
    print(qry)
    res33=db.selectOne("select * from likes where userid= '"+lid+"'")
    stat=""
    if res33 is None:
        stat="none"
    else:
        stat="liked"
    if res is not None:
        return jsonify(status="ok", users=res,nooflikes=res22['llk'], stat=stat)
    else:
        return jsonify(status="no")

@app.route("/send_like", methods=['post'])
def send_like():
    psid = request.form['postid']
    lid = request.form['lid']
    stat = request.form['stat']
    db=Db()
    if stat=="Like Now":
        db.insert("insert into likes(userid,likeno,postid) values('"+lid+"',1,'"+psid+"')")
    else:
        db.delete("delete from likes where userid='"+lid+"' and postid='"+psid+"'")
    return jsonify(status='ok')


##
@app.route("/view_recieve_wrk_request", methods=['post'])
def view_recieve_wrk_request():
    lid = request.form['lid']
    db=Db()
    res=db.select("select request.*, view_user.name, view_user.phone_no from view_user,request where request.from_id=view_user.loginid and request.to_id='"+lid+"'")
    if res is not None:
        return jsonify(status="ok", users=res)
    else:
        return jsonify(status="no")





@app.route("/usr_acc_wrk_request", methods=['post'])
def usr_acc_wrk_request():
    rid = request.form['rid']
    db=Db()
    db.update("update request set status='accepted' where rid='"+rid+"'")
    return jsonify(status="ok" )


@app.route("/usr_rej_wrk_request", methods=['post'])
def usr_rej_wrk_request():
    rid = request.form['rid']
    db=Db()
    db.update("update request set status='rejected' where rid='"+rid+"'")
    return jsonify(status="ok")

@app.route("/usr_com_wrk_request", methods=['post'])
def usr_com_wrk_request():
    rid = request.form['rid']
    db=Db()
    db.update("update request set status='completed' where rid='"+rid+"'")
    return jsonify(status="ok")



@app.route("/view_sent_wrk_request", methods=['post'])
def view_sent_wrk_request():
    lid = request.form['lid']
    db=Db()
    res=db.select("select request.*, view_user.name, skill.skill from view_user,request, skill where skill.skill_id=view_user.skill_id and request.to_id=view_user.loginid and request.from_id='"+lid+"'")
    if res is not None:
        return jsonify(status="ok", users=res)
    else:
        return jsonify(status="no")

@app.route("/user_del_work_req", methods=['post'])
def user_del_work_req():
    rid=request.form['rid']
    db=Db()
    db.delete("delete from request where rid='"+rid+"'")
    return jsonify(status="ok")

@app.route("/user_send_work_req", methods=['post'])
def user_send_work_req():
    lid=request.form['lid']
    toid=request.form['toid']
    db=Db()
    res=db.selectOne("select * from request where from_id='"+lid+"' and to_id='"+toid+"' and status='pending'")
    if res is None:
        db.insert("insert into request(date, from_id, to_id, status) values(curdate(),'"+lid+"', '"+toid+"', 'pending')")
        return jsonify(status="ok")
    else:
        return jsonify(status='no')

@app.route("/send_rating", methods=['post'])
def send_rating():
    lid=request.form['lid']
    toid=request.form['toid']
    rate=request.form['rate']
    db=Db()
    res=db.selectOne("select * from rating where from_id='"+lid+"' and to_id='"+toid+"'")
    if res is None:
        db.insert("insert into rating(date,from_id,to_id,rate) values(curdate(),'"+lid+"','"+toid+"','"+rate+"')")
    else:
        db.update("update rating set date=curdate(), rate='"+rate+"' where rate_id='"+str(res['rate_id'])+"'")
    return jsonify(status="ok")


@app.route("/skill_load", methods=['post'])
def skill_load():
    db=Db()
    res=db.select("select * from skill")
    return jsonify(status="ok", data=res)

@app.route("/worker_load", methods=['post'])
def worker_load():
    lid=request.form['lid']
    wid=request.form['wid']
    lat=request.form['lat']
    logi=request.form['logi']
    db=Db()
    ar=[]
    res=db.select("SELECT view_user.*, SQRT( POW(69.1 * (latitude - '"+lat+"'), 2) +POW(69.1 * ('"+logi+"' - longitude) * COS(latitude/ 57.3), 2)) AS distance FROM view_user,user_location where user_location.lid=view_user.loginid and view_user.loginid!='"+lid+"' and view_user.skill_id='"+wid+"' ORDER BY distance")
    for i in res:
        r = {}
        rr=db.selectOne("select avg(rate) as rate from rating where to_id='"+str(i['loginid'])+"'")
        if rr['rate'] is None:
            r['rating']='0'
        else:
            r['rating']=rr['rate']
        r['image']=i['image']
        r['name']=i['name']
        r['phone']=i['phone_no']
        r['email']=i['email']
        r['lid']=i['loginid']
        ar.append(r)
    print(ar)
    if len(ar)==0:
        return jsonify(status='no')
    else:
        return jsonify(status="ok", data=ar)



if __name__ == '__main__':
    app.run(debug=True,host='0.0.0.0')
