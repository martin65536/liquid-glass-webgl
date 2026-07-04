package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class m2 extends sz0 implements kv {
    public final /* synthetic */ int i;
    public int j;
    public Object k;
    public Object l;
    public Object m;
    public /* synthetic */ Object n;
    public final /* synthetic */ Object o;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ m2(Object obj, Object obj2, Object obj3, ij ijVar, int i) {
        super(2, ijVar);
        this.i = i;
        this.m = obj;
        this.n = obj2;
        this.o = obj3;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        int i = this.i;
        x31 x31Var = x31.a;
        switch (i) {
            case 0:
                return ((m2) i((ij) obj2, (hk) obj)).k(x31Var);
            case 1:
                return ((m2) i((ij) obj2, (hk) obj)).k(x31Var);
            case 2:
                ((m2) i((ij) obj2, (ps) obj)).k(x31Var);
                return ik.e;
            default:
                return ((m2) i((ij) obj2, (fu0) obj)).k(x31Var);
        }
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        int i = this.i;
        Object obj2 = this.o;
        switch (i) {
            case 0:
                m2 m2Var = new m2((hx) this.m, (y6) this.n, (y6) obj2, ijVar, 0);
                m2Var.k = obj;
                return m2Var;
            case 1:
                m2 m2Var2 = new m2((to0) this.m, (so0) this.n, (q6) obj2, ijVar, 1);
                m2Var2.k = obj;
                return m2Var2;
            case 2:
                m2 m2Var3 = new m2((vu) obj2, ijVar);
                m2Var3.n = obj;
                return m2Var3;
            default:
                m2 m2Var4 = new m2((p21) this.m, (hu0) this.n, (ep0) obj2, ijVar, 3);
                m2Var4.k = obj;
                return m2Var4;
        }
    }

    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Not initialized variable reg: 2, insn: 0x0309: IF  (r4v9 ?? I:??[int, boolean, OBJECT, ARRAY, byte, short, char]) != (r2 I:??[int, boolean, OBJECT, ARRAY, byte, short, char])  -> B:130:0x0311 (LINE:778), block:B:128:0x0309 */
    /* JADX WARN: Removed duplicated region for block: B:115:0x02cb  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x036c  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x03d1  */
    /* JADX WARN: Removed duplicated region for block: B:162:0x03da A[LOOP:3: B:161:0x03d8->B:162:0x03da, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:166:0x044d  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x0458  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0199  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x01a4 A[Catch: all -> 0x013b, TRY_LEAVE, TryCatch #0 {all -> 0x013b, blocks: (B:34:0x0153, B:35:0x019a, B:37:0x0189, B:42:0x01a4, B:47:0x0137, B:49:0x0173), top: B:25:0x0120 }] */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0286 A[Catch: all -> 0x01ec, TryCatch #1 {all -> 0x01ec, blocks: (B:67:0x01e7, B:94:0x0277, B:96:0x0286, B:98:0x0294, B:100:0x029a, B:102:0x02a0, B:104:0x02a4, B:106:0x02a8, B:111:0x02ab, B:113:0x02ae), top: B:62:0x01d8 }] */
    /* JADX WARN: Type inference failed for: r1v21, types: [g2, int] */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [int] */
    /* JADX WARN: Type inference failed for: r3v29 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:152:0x044d -> B:132:0x044f). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:18:0x0083 -> B:7:0x0085). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:34:0x01a2 -> B:35:0x0189). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:41:0x01b6 -> B:35:0x0189). Please report as a decompilation issue!!! */
    @Override // defpackage.s9
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object k(java.lang.Object r26) {
        /*
            Method dump skipped, instructions count: 1126
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.m2.k(java.lang.Object):java.lang.Object");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public m2(vu vuVar, ij ijVar) {
        super(2, ijVar);
        this.i = 2;
        this.o = vuVar;
    }
}
