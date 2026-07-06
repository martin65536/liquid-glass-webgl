package defpackage;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class po0 {
    public static final po0 e;
    public static final po0 f;
    public static final po0 g;
    public static final po0 h;
    public static final po0 i;
    public static final po0 j;
    public static final /* synthetic */ po0[] k;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [po0, java.lang.Enum] */
    /* JADX WARN: Type inference failed for: r1v1, types: [po0, java.lang.Enum] */
    /* JADX WARN: Type inference failed for: r3v1, types: [po0, java.lang.Enum] */
    /* JADX WARN: Type inference failed for: r5v1, types: [po0, java.lang.Enum] */
    /* JADX WARN: Type inference failed for: r7v1, types: [po0, java.lang.Enum] */
    /* JADX WARN: Type inference failed for: r9v1, types: [po0, java.lang.Enum] */
    static {
        ?? r0 = new Enum("ShutDown", 0);
        e = r0;
        ?? r1 = new Enum("ShuttingDown", 1);
        f = r1;
        ?? r3 = new Enum("Inactive", 2);
        g = r3;
        ?? r5 = new Enum("InactivePendingWork", 3);
        h = r5;
        ?? r7 = new Enum("Idle", 4);
        i = r7;
        ?? r9 = new Enum("PendingWork", 5);
        j = r9;
        k = new po0[]{r0, r1, r3, r5, r7, r9};
    }

    public static po0 valueOf(String str) {
        return (po0) Enum.valueOf(po0.class, str);
    }

    public static po0[] values() {
        return (po0[]) k.clone();
    }
}
