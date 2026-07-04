package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class j extends sz0 implements kv {
    public final /* synthetic */ int i;
    public int j;
    public final /* synthetic */ de k;
    public final /* synthetic */ on0 l;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ j(de deVar, on0 on0Var, ij ijVar, int i) {
        super(2, ijVar);
        this.i = i;
        this.k = deVar;
        this.l = on0Var;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        int i = this.i;
        x31 x31Var = x31.a;
        hk hkVar = (hk) obj;
        ij ijVar = (ij) obj2;
        switch (i) {
            case 0:
                return ((j) i(ijVar, hkVar)).k(x31Var);
            case 1:
                return ((j) i(ijVar, hkVar)).k(x31Var);
            case 2:
                return ((j) i(ijVar, hkVar)).k(x31Var);
            default:
                return ((j) i(ijVar, hkVar)).k(x31Var);
        }
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        int i = this.i;
        on0 on0Var = this.l;
        de deVar = this.k;
        switch (i) {
            case 0:
                return new j(deVar, on0Var, ijVar, 0);
            case 1:
                return new j(deVar, on0Var, ijVar, 1);
            case 2:
                return new j(deVar, on0Var, ijVar, 2);
            default:
                return new j(deVar, on0Var, ijVar, 3);
        }
    }

    @Override // defpackage.s9
    public final Object k(Object obj) {
        int i = this.i;
        x31 x31Var = x31.a;
        on0 on0Var = this.l;
        de deVar = this.k;
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
                je0 je0Var = deVar.u;
                if (je0Var != null) {
                    nn0 nn0Var = new nn0(on0Var);
                    this.j = 1;
                    if (je0Var.a(nn0Var, this) == ikVar) {
                        return ikVar;
                    }
                    return x31Var;
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
                je0 je0Var2 = deVar.u;
                if (je0Var2 != null) {
                    nn0 nn0Var2 = new nn0(on0Var);
                    this.j = 1;
                    if (je0Var2.a(nn0Var2, this) == ikVar) {
                        return ikVar;
                    }
                    return x31Var;
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
                je0 je0Var3 = deVar.u;
                if (je0Var3 != null) {
                    this.j = 1;
                    if (je0Var3.a(on0Var, this) == ikVar) {
                        return ikVar;
                    }
                    return x31Var;
                }
                return x31Var;
            default:
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
                je0 je0Var4 = deVar.u;
                if (je0Var4 != null) {
                    pn0 pn0Var = new pn0(on0Var);
                    this.j = 1;
                    if (je0Var4.a(pn0Var, this) == ikVar) {
                        return ikVar;
                    }
                    return x31Var;
                }
                return x31Var;
        }
    }
}
