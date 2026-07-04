package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class e60 {
    public final cs0 a;
    public final i60 b;
    public final ve0 c;

    public e60(cs0 cs0Var, i60 i60Var) {
        this.a = cs0Var;
        this.b = i60Var;
        long[] jArr = zs0.a;
        this.c = new ve0();
    }

    public final kv a(int i, Object obj, Object obj2) {
        ve0 ve0Var = this.c;
        d60 d60Var = (d60) ve0Var.g(obj);
        int i2 = 3;
        if (d60Var != null && d60Var.c == i && o20.e(d60Var.b, obj2)) {
            gg ggVar = d60Var.d;
            if (ggVar == null) {
                gg ggVar2 = new gg(818252804, true, new eb(i2, d60Var.e, d60Var));
                d60Var.d = ggVar2;
                return ggVar2;
            }
            return ggVar;
        }
        d60 d60Var2 = new d60(this, i, obj, obj2);
        ve0Var.m(obj, d60Var2);
        gg ggVar3 = d60Var2.d;
        if (ggVar3 == null) {
            gg ggVar4 = new gg(818252804, true, new eb(i2, this, d60Var2));
            d60Var2.d = ggVar4;
            return ggVar4;
        }
        return ggVar3;
    }

    public final Object b(Object obj) {
        if (obj != null) {
            d60 d60Var = (d60) this.c.g(obj);
            if (d60Var != null) {
                return d60Var.b;
            }
            c70 c70Var = (c70) this.b.a();
            int c = c70Var.d.c(obj);
            if (c != -1) {
                return c70Var.b(c);
            }
            return null;
        }
        return null;
    }
}
