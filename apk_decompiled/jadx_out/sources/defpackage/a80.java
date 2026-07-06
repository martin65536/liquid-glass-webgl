package defpackage;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class a80 {
    public static final a80 e;
    public static final a80 f;
    public static final a80 g;
    public static final a80 h;
    public static final a80 i;
    public static final /* synthetic */ a80[] j;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.Enum, a80] */
    /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Enum, a80] */
    /* JADX WARN: Type inference failed for: r3v1, types: [java.lang.Enum, a80] */
    /* JADX WARN: Type inference failed for: r5v1, types: [java.lang.Enum, a80] */
    /* JADX WARN: Type inference failed for: r7v1, types: [java.lang.Enum, a80] */
    static {
        ?? r0 = new Enum("DESTROYED", 0);
        e = r0;
        ?? r1 = new Enum("INITIALIZED", 1);
        f = r1;
        ?? r3 = new Enum("CREATED", 2);
        g = r3;
        ?? r5 = new Enum("STARTED", 3);
        h = r5;
        ?? r7 = new Enum("RESUMED", 4);
        i = r7;
        j = new a80[]{r0, r1, r3, r5, r7};
    }

    public static a80 valueOf(String str) {
        return (a80) Enum.valueOf(a80.class, str);
    }

    public static a80[] values() {
        return (a80[]) j.clone();
    }
}
