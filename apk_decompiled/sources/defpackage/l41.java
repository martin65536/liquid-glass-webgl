package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class l41 extends h41 {
    public final sx b;
    public String c;
    public boolean d;
    public final rp e;
    public vu f;
    public final ik0 g;
    public da h;
    public final ik0 i;
    public long j;
    public float k;
    public float l;
    public final k41 m;

    public l41(sx sxVar) {
        this.b = sxVar;
        sxVar.i = new k41(this, 0);
        this.c = "";
        this.d = true;
        this.e = new rp();
        this.f = ba0.r;
        this.g = n30.B(null);
        this.i = n30.B(new mw0(0L));
        this.j = 9205357640488583168L;
        this.k = 1.0f;
        this.l = 1.0f;
        this.m = new k41(this, 1);
    }

    @Override // defpackage.h41
    public final void a(up upVar) {
        e(upVar, 1.0f, null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:48:0x007d, code lost:
    
        if (r9 == r13) goto L49;
     */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x01b4  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x01d0  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x01b7  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00ac  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void e(defpackage.up r32, float r33, defpackage.te r34) {
        /*
            Method dump skipped, instructions count: 489
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.l41.e(up, float, te):void");
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("Params: \tname: ");
        sb.append(this.c);
        sb.append("\n\tviewportWidth: ");
        ik0 ik0Var = this.i;
        sb.append(Float.intBitsToFloat((int) (((mw0) ik0Var.getValue()).a >> 32)));
        sb.append("\n\tviewportHeight: ");
        sb.append(Float.intBitsToFloat((int) (((mw0) ik0Var.getValue()).a & 4294967295L)));
        sb.append("\n");
        return sb.toString();
    }
}
