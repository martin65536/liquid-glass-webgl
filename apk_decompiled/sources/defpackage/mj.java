package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class mj extends sz0 implements kv {
    public final /* synthetic */ int i;
    public int j;
    public final /* synthetic */ Object k;
    public /* synthetic */ float l;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ mj(y6 y6Var, float f, ij ijVar, int i) {
        super(2, ijVar);
        this.i = i;
        this.k = y6Var;
        this.l = f;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        int i = this.i;
        x31 x31Var = x31.a;
        switch (i) {
            case 0:
                return ((mj) i((ij) obj2, (hk) obj)).k(x31Var);
            case 1:
                return ((mj) i((ij) obj2, (hk) obj)).k(x31Var);
            case 2:
                return ((mj) i((ij) obj2, (hk) obj)).k(x31Var);
            default:
                return ((mj) i((ij) obj2, Float.valueOf(((Number) obj).floatValue()))).k(x31Var);
        }
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        int i = this.i;
        Object obj2 = this.k;
        switch (i) {
            case 0:
                return new mj((y6) obj2, this.l, ijVar, 0);
            case 1:
                return new mj((y6) obj2, this.l, ijVar, 1);
            case 2:
                return new mj((y6) obj2, this.l, ijVar, 2);
            default:
                mj mjVar = new mj((dh) obj2, ijVar);
                mjVar.l = ((Number) obj).floatValue();
                return mjVar;
        }
    }

    @Override // defpackage.s9
    public final Object k(Object obj) {
        Object d;
        int i = this.i;
        float f = 1.0f;
        float f2 = 0.0f;
        x31 x31Var = x31.a;
        ik ikVar = ik.e;
        Object obj2 = this.k;
        Object obj3 = null;
        switch (i) {
            case 0:
                int i2 = this.j;
                if (i2 != 0) {
                    if (i2 == 1) {
                        o30.x(obj);
                        return x31Var;
                    }
                    v7.o("call to 'resume' before 'invoke' with coroutine");
                    return null;
                }
                o30.x(obj);
                Float f3 = new Float(this.l);
                this.j = 1;
                if (((y6) obj2).e(f3, this) == ikVar) {
                    return ikVar;
                }
                return x31Var;
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
                y6 y6Var = (y6) obj2;
                float f4 = this.l;
                if (f4 >= 0.0f) {
                    f2 = f4;
                }
                if (f2 <= 1.0f) {
                    f = f2;
                }
                Float f5 = new Float(f);
                this.j = 1;
                if (y6Var.e(f5, this) == ikVar) {
                    return ikVar;
                }
                return x31Var;
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
                y6 y6Var2 = (y6) obj2;
                Float f6 = new Float(this.l);
                ay0 ay0Var = new ay0(1.0f, 300.0f, new Float(0.01f));
                this.j = 1;
                if (y6.c(y6Var2, f6, ay0Var, null, null, this, 12) == ikVar) {
                    return ikVar;
                }
                return x31Var;
            default:
                dh dhVar = (dh) obj2;
                int i5 = this.j;
                if (i5 != 0) {
                    if (i5 == 1) {
                        o30.x(obj);
                        d = obj;
                    } else {
                        v7.o("call to 'resume' before 'invoke' with coroutine");
                        return null;
                    }
                } else {
                    o30.x(obj);
                    float f7 = this.l;
                    Object g = dhVar.a.d.e.g(mu0.e);
                    if (g != null) {
                        obj3 = g;
                    }
                    kv kvVar = (kv) obj3;
                    if (kvVar != null) {
                        ch0 ch0Var = new ch0((Float.floatToRawIntBits(0.0f) << 32) | (Float.floatToRawIntBits(f7) & 4294967295L));
                        this.j = 1;
                        d = kvVar.d(ch0Var, this);
                        if (d == ikVar) {
                            return ikVar;
                        }
                    } else {
                        throw d3.t("Required value was null.");
                    }
                }
                return new Float(Float.intBitsToFloat((int) (((ch0) d).a & 4294967295L)));
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public mj(dh dhVar, ij ijVar) {
        super(2, ijVar);
        this.i = 3;
        this.k = dhVar;
    }
}
