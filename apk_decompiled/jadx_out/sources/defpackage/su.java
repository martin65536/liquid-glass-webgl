package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class su extends iq0 implements kv {
    public final /* synthetic */ int g;
    public Object h;
    public int i;
    public /* synthetic */ Object j;
    public final /* synthetic */ Object k;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ su(Object obj, Object obj2, ij ijVar, int i) {
        super(ijVar);
        this.g = i;
        this.j = obj;
        this.k = obj2;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        int i = this.g;
        x31 x31Var = x31.a;
        switch (i) {
            case 0:
                return ((su) i((ij) obj2, (xz0) obj)).k(x31Var);
            case 1:
                return ((su) i((ij) obj2, (mv0) obj)).k(x31Var);
            default:
                return ((su) i((ij) obj2, (xz0) obj)).k(x31Var);
        }
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        int i = this.g;
        Object obj2 = this.k;
        switch (i) {
            case 0:
                su suVar = new su((yj) this.j, (kv) obj2, ijVar, 0);
                suVar.h = obj;
                return suVar;
            case 1:
                su suVar2 = new su((vu) obj2, ijVar);
                suVar2.j = obj;
                return suVar2;
            default:
                su suVar3 = new su((qm0) this.j, (ep0) obj2, ijVar, 2);
                suVar3.h = obj;
                return suVar3;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x004e, code lost:
    
        if (r5 != r6) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00a3, code lost:
    
        if (r5 == r6) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:?, code lost:
    
        return r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x0160, code lost:
    
        if (r0 != r6) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x0171, code lost:
    
        if (r0 == r6) goto L84;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00fc  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0107  */
    /* JADX WARN: Removed duplicated region for block: B:63:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x014a A[Catch: CancellationException -> 0x0131, TRY_ENTER, TryCatch #0 {CancellationException -> 0x0131, blocks: (B:77:0x014a, B:82:0x0158, B:89:0x012d, B:91:0x0138), top: B:68:0x0113 }] */
    /* JADX WARN: Removed duplicated region for block: B:85:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r5v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r5v22 */
    /* JADX WARN: Type inference failed for: r5v23 */
    /* JADX WARN: Type inference failed for: r5v24 */
    /* JADX WARN: Type inference failed for: r5v3, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r5v4 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:30:0x00a3 -> B:8:0x00a7). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:49:0x0107 -> B:45:0x0108). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:68:0x0160 -> B:61:0x0144). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:81:0x0171 -> B:61:0x0144). Please report as a decompilation issue!!! */
    @Override // defpackage.s9
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object k(java.lang.Object r17) {
        /*
            Method dump skipped, instructions count: 384
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.su.k(java.lang.Object):java.lang.Object");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public su(vu vuVar, ij ijVar) {
        super(ijVar);
        this.g = 1;
        this.k = vuVar;
    }
}
