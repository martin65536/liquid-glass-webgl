package defpackage;

import androidx.compose.ui.input.pointer.PointerInputEventHandler;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class m8 extends sz0 implements kv {
    public final /* synthetic */ int i;
    public int j;
    public /* synthetic */ Object k;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public m8(m70 m70Var, int i, ij ijVar) {
        super(2, ijVar);
        this.i = 4;
        this.k = m70Var;
        this.j = i;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        int i = this.i;
        x31 x31Var = x31.a;
        switch (i) {
            case 0:
                return ((m8) i((ij) obj2, (hk) obj)).k(x31Var);
            case 1:
                return ((m8) i((ij) obj2, (hk) obj)).k(x31Var);
            case 2:
                return ((m8) i((ij) obj2, (hk) obj)).k(x31Var);
            case 3:
                return ((m8) i((ij) obj2, (hk) obj)).k(x31Var);
            case 4:
                ((m8) i((ij) obj2, (lt0) obj)).k(x31Var);
                return x31Var;
            case 5:
                ((m8) i((ij) obj2, Integer.valueOf(((Number) obj).intValue()))).k(x31Var);
                return x31Var;
            case 6:
                return ((m8) i((ij) obj2, (hk) obj)).k(x31Var);
            case 7:
                return ((m8) i((ij) obj2, (hk) obj)).k(x31Var);
            case 8:
                return ((m8) i((ij) obj2, (hk) obj)).k(x31Var);
            default:
                return ((m8) i((ij) obj2, (hk) obj)).k(x31Var);
        }
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        int i = 2;
        switch (this.i) {
            case 0:
                return new m8((gv) this.k, ijVar, 0);
            case 1:
                return new m8((hl) this.k, ijVar, 1);
            case 2:
                return new m8((tt) this.k, ijVar, i);
            case 3:
                return new m8((c4) this.k, ijVar, 3);
            case 4:
                return new m8((m70) this.k, this.j, ijVar);
            case 5:
                m8 m8Var = new m8((fk0) this.k, ijVar, 5);
                m8Var.j = ((Number) obj).intValue();
                return m8Var;
            case 6:
                return new m8((ud0) this.k, ijVar, 6);
            case 7:
                m8 m8Var2 = new m8(i, ijVar);
                m8Var2.k = obj;
                return m8Var2;
            case 8:
                return new m8((yz0) this.k, ijVar, 8);
            default:
                return new m8((mn0) this.k, ijVar, 9);
        }
    }

    /* JADX WARN: Type inference failed for: r7v0, types: [java.lang.Object, java.io.Serializable] */
    @Override // defpackage.s9
    public final Object k(Object obj) {
        hk hkVar;
        int i = this.i;
        x31 x31Var = x31.a;
        ik ikVar = ik.e;
        ij ijVar = null;
        switch (i) {
            case 0:
                int i2 = this.j;
                if (i2 != 0) {
                    if (i2 == 1) {
                        o30.x(obj);
                        return obj;
                    }
                    v7.o("call to 'resume' before 'invoke' with coroutine");
                    return null;
                }
                o30.x(obj);
                gv gvVar = (gv) this.k;
                this.j = 1;
                Object e = gvVar.e(this);
                if (e == ikVar) {
                    return ikVar;
                }
                return e;
            case 1:
                int i3 = this.j;
                if (i3 != 0) {
                    if (i3 == 1) {
                        o30.x(obj);
                        return x31Var;
                    }
                    v7.o("call to 'resume' before 'invoke' with coroutine");
                    return null;
                }
                o30.x(obj);
                ?? obj2 = new Object();
                Object obj3 = new Object();
                Object obj4 = new Object();
                hl hlVar = (hl) this.k;
                ew0 ew0Var = hlVar.s.a;
                kd kdVar = new kd(obj2, obj3, obj4, hlVar, 1);
                this.j = 1;
                ew0Var.getClass();
                ew0.k(ew0Var, kdVar, this);
                return ikVar;
            case 2:
                int i4 = this.j;
                if (i4 != 0) {
                    if (i4 == 1) {
                        o30.x(obj);
                        return x31Var;
                    }
                    v7.o("call to 'resume' before 'invoke' with coroutine");
                    return null;
                }
                o30.x(obj);
                tt ttVar = (tt) this.k;
                this.j = 1;
                if (o20.f(ttVar, null, this) == ikVar) {
                    return ikVar;
                }
                return x31Var;
            case 3:
                int i5 = this.j;
                if (i5 != 0) {
                    if (i5 == 1) {
                        o30.x(obj);
                        return x31Var;
                    }
                    v7.o("call to 'resume' before 'invoke' with coroutine");
                    return null;
                }
                o30.x(obj);
                d7 d7Var = (d7) ((c4) this.k).g;
                Float f = new Float(0.0f);
                ay0 L = k81.L(1, new Float(0.5f));
                this.j = 1;
                if (d20.g(d7Var, f, L, new ts0(28), this) == ikVar) {
                    return ikVar;
                }
                return x31Var;
            case 4:
                o30.x(obj);
                m70 m70Var = (m70) this.k;
                int i6 = this.j;
                ud udVar = m70Var.e;
                if (((fk0) udVar.b).g() != i6 || ((fk0) udVar.c).g() != 0) {
                    c60 c60Var = m70Var.n;
                    c60Var.c();
                    c60Var.b = null;
                }
                udVar.b(i6, 0);
                udVar.d = null;
                z40 z40Var = m70Var.k;
                if (z40Var != null) {
                    z40Var.k();
                }
                return x31Var;
            case 5:
                int i7 = this.j;
                o30.x(obj);
                ((fk0) this.k).h(i7);
                return x31Var;
            case 6:
                int i8 = this.j;
                if (i8 != 0) {
                    if (i8 == 1) {
                        o30.x(obj);
                        return obj;
                    }
                    v7.o("call to 'resume' before 'invoke' with coroutine");
                    return null;
                }
                o30.x(obj);
                zb zbVar = ((ud0) this.k).g;
                this.j = 1;
                Object q = dl.q(new d(zbVar, ijVar, 15), this);
                if (q == ikVar) {
                    return ikVar;
                }
                return q;
            case 7:
                int i9 = this.j;
                if (i9 != 0) {
                    if (i9 == 1) {
                        hkVar = (hk) this.k;
                        o30.x(obj);
                    } else {
                        v7.o("call to 'resume' before 'invoke' with coroutine");
                        return null;
                    }
                } else {
                    o30.x(obj);
                    hkVar = (hk) this.k;
                }
                while (g30.w(hkVar.g())) {
                    pb pbVar = new pb(14);
                    this.k = hkVar;
                    this.j = 1;
                    yj yjVar = this.f;
                    yjVar.getClass();
                    if (o30.r(yjVar).d(pbVar, this) == ikVar) {
                        return ikVar;
                    }
                }
                return x31Var;
            case 8:
                yz0 yz0Var = (yz0) this.k;
                int i10 = this.j;
                if (i10 != 0) {
                    if (i10 == 1 || i10 == 2) {
                        o30.x(obj);
                        return x31Var;
                    }
                    v7.o("call to 'resume' before 'invoke' with coroutine");
                    return null;
                }
                o30.x(obj);
                PointerInputEventHandler pointerInputEventHandler = yz0Var.u;
                this.j = 2;
                if (pointerInputEventHandler.invoke(yz0Var, this) == ikVar) {
                    return ikVar;
                }
                return x31Var;
            default:
                int i11 = this.j;
                if (i11 != 0) {
                    if (i11 == 1) {
                        o30.x(obj);
                        return x31Var;
                    }
                    v7.o("call to 'resume' before 'invoke' with coroutine");
                    return null;
                }
                o30.x(obj);
                mn0 mn0Var = (mn0) this.k;
                this.j = 1;
                if (mn0Var.u(this) == ikVar) {
                    return ikVar;
                }
                return x31Var;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ m8(int i, ij ijVar) {
        super(i, ijVar);
        this.i = 7;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ m8(Object obj, ij ijVar, int i) {
        super(2, ijVar);
        this.i = i;
        this.k = obj;
    }
}
