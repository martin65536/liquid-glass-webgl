package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class h extends sz0 implements kv {
    public final /* synthetic */ int i;
    public int j;
    public final /* synthetic */ je0 k;
    public final /* synthetic */ on0 l;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public h(on0 on0Var, je0 je0Var, ij ijVar) {
        super(2, ijVar);
        this.i = 0;
        this.l = on0Var;
        this.k = je0Var;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        int i = this.i;
        x31 x31Var = x31.a;
        hk hkVar = (hk) obj;
        ij ijVar = (ij) obj2;
        switch (i) {
            case 0:
                return ((h) i(ijVar, hkVar)).k(x31Var);
            case 1:
                return ((h) i(ijVar, hkVar)).k(x31Var);
            default:
                return ((h) i(ijVar, hkVar)).k(x31Var);
        }
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        int i = this.i;
        on0 on0Var = this.l;
        je0 je0Var = this.k;
        switch (i) {
            case 0:
                return new h(on0Var, je0Var, ijVar);
            case 1:
                return new h(je0Var, on0Var, ijVar, 1);
            default:
                return new h(je0Var, on0Var, ijVar, 2);
        }
    }

    @Override // defpackage.s9
    public final Object k(Object obj) {
        int i = this.i;
        x31 x31Var = x31.a;
        on0 on0Var = this.l;
        je0 je0Var = this.k;
        ik ikVar = ik.e;
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
                pn0 pn0Var = new pn0(on0Var);
                this.j = 1;
                if (je0Var.a(pn0Var, this) == ikVar) {
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
                this.j = 1;
                if (je0Var.a(on0Var, this) == ikVar) {
                    return ikVar;
                }
                return x31Var;
            default:
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
                this.j = 1;
                if (je0Var.a(on0Var, this) == ikVar) {
                    return ikVar;
                }
                return x31Var;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ h(je0 je0Var, on0 on0Var, ij ijVar, int i) {
        super(2, ijVar);
        this.i = i;
        this.k = je0Var;
        this.l = on0Var;
    }
}
