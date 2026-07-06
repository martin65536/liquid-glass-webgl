package defpackage;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class fk {
    public static final fk e;
    public static final fk f;
    public static final fk g;
    public static final fk h;
    public static final fk i;
    public static final /* synthetic */ fk[] j;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.Enum, fk] */
    /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Enum, fk] */
    /* JADX WARN: Type inference failed for: r3v1, types: [java.lang.Enum, fk] */
    /* JADX WARN: Type inference failed for: r5v1, types: [java.lang.Enum, fk] */
    /* JADX WARN: Type inference failed for: r7v1, types: [java.lang.Enum, fk] */
    static {
        ?? r0 = new Enum("CPU_ACQUIRED", 0);
        e = r0;
        ?? r1 = new Enum("BLOCKING", 1);
        f = r1;
        ?? r3 = new Enum("PARKING", 2);
        g = r3;
        ?? r5 = new Enum("DORMANT", 3);
        h = r5;
        ?? r7 = new Enum("TERMINATED", 4);
        i = r7;
        j = new fk[]{r0, r1, r3, r5, r7};
    }

    public static fk valueOf(String str) {
        return (fk) Enum.valueOf(fk.class, str);
    }

    public static fk[] values() {
        return (fk[]) j.clone();
    }
}
